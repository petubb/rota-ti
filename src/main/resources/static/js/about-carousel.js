(() => {
    const carousel = document.querySelector("[data-about-carousel]");

    if (!carousel) {
        return;
    }

    const viewport = carousel.querySelector("[data-carousel-viewport]");
    const track = carousel.querySelector(".about-carousel-track");
    const slides = Array.from(carousel.querySelectorAll(".about-carousel-slide"));
    const previousButton = carousel.querySelector("[data-carousel-previous]");
    const nextButton = carousel.querySelector("[data-carousel-next]");
    const toggleButton = carousel.querySelector("[data-carousel-toggle]");
    const currentLabel = carousel.querySelector("[data-carousel-current]");
    const reducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)");
    const autoAdvanceDelay = 6500;

    let currentIndex = 0;
    let autoAdvanceTimer;
    let scrollTimer;
    let interactionPaused = false;
    let manuallyPaused = reducedMotion.matches;
    let carouselIsVisible = typeof IntersectionObserver === "undefined";

    if (!viewport || !track || slides.length < 2) {
        return;
    }

    const normalizeIndex = (index) => (index + slides.length) % slides.length;

    const updateCurrentLabel = () => {
        currentLabel.textContent = String(currentIndex + 1).padStart(2, "0");
    };

    const updateToggleButton = () => {
        toggleButton.setAttribute("aria-pressed", String(manuallyPaused));
        toggleButton.textContent = manuallyPaused ? "Iniciar movimento" : "Pausar movimento";
    };

    const clearAutoAdvance = () => {
        window.clearTimeout(autoAdvanceTimer);
    };

    const canAutoAdvance = () => (
        !manuallyPaused
        && !interactionPaused
        && carouselIsVisible
        && !document.hidden
    );

    const scheduleAutoAdvance = () => {
        clearAutoAdvance();

        if (!canAutoAdvance()) {
            return;
        }

        autoAdvanceTimer = window.setTimeout(() => {
            showSlide(currentIndex + 1, false);
            scheduleAutoAdvance();
        }, autoAdvanceDelay);
    };

    const slideOffset = (slide) => (
        slide.getBoundingClientRect().left - track.getBoundingClientRect().left
    );

    const showSlide = (index, restartTimer = true) => {
        currentIndex = normalizeIndex(index);
        viewport.scrollTo({
            left: slideOffset(slides[currentIndex]),
            behavior: reducedMotion.matches ? "auto" : "smooth"
        });
        updateCurrentLabel();

        if (restartTimer) {
            scheduleAutoAdvance();
        }
    };

    const syncIndexWithScroll = () => {
        const viewportLeft = viewport.getBoundingClientRect().left;
        let closestIndex = 0;
        let closestDistance = Number.POSITIVE_INFINITY;

        slides.forEach((slide, index) => {
            const distance = Math.abs(slide.getBoundingClientRect().left - viewportLeft);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestIndex = index;
            }
        });

        currentIndex = closestIndex;
        updateCurrentLabel();
    };

    const updateTrackEndSpace = () => {
        const lastSlide = slides[slides.length - 1];
        const endSpace = Math.max(0, viewport.clientWidth - lastSlide.offsetWidth - 4);
        track.style.paddingRight = `${endSpace}px`;
    };

    previousButton.addEventListener("click", () => showSlide(currentIndex - 1));
    nextButton.addEventListener("click", () => showSlide(currentIndex + 1));

    toggleButton.addEventListener("click", () => {
        manuallyPaused = !manuallyPaused;
        updateToggleButton();
        scheduleAutoAdvance();
    });

    viewport.addEventListener("keydown", (event) => {
        if (event.key === "ArrowLeft") {
            event.preventDefault();
            showSlide(currentIndex - 1);
        }

        if (event.key === "ArrowRight") {
            event.preventDefault();
            showSlide(currentIndex + 1);
        }
    });

    viewport.addEventListener("scroll", () => {
        window.clearTimeout(scrollTimer);
        scrollTimer = window.setTimeout(() => {
            syncIndexWithScroll();
            scheduleAutoAdvance();
        }, 140);
    }, { passive: true });

    carousel.addEventListener("mouseenter", () => {
        interactionPaused = true;
        clearAutoAdvance();
    });

    carousel.addEventListener("mouseleave", () => {
        interactionPaused = false;
        scheduleAutoAdvance();
    });

    carousel.addEventListener("focusin", () => {
        interactionPaused = true;
        clearAutoAdvance();
    });

    carousel.addEventListener("focusout", (event) => {
        if (!carousel.contains(event.relatedTarget)) {
            interactionPaused = false;
            scheduleAutoAdvance();
        }
    });

    document.addEventListener("visibilitychange", scheduleAutoAdvance);

    reducedMotion.addEventListener("change", (event) => {
        if (event.matches) {
            manuallyPaused = true;
        }
        updateToggleButton();
        scheduleAutoAdvance();
    });

    if (typeof ResizeObserver !== "undefined") {
        new ResizeObserver(updateTrackEndSpace).observe(viewport);
    } else {
        window.addEventListener("resize", updateTrackEndSpace);
    }

    if (typeof IntersectionObserver !== "undefined") {
        new IntersectionObserver((entries) => {
            carouselIsVisible = entries[0].isIntersecting;
            scheduleAutoAdvance();
        }, { threshold: 0.25 }).observe(carousel);
    }

    updateTrackEndSpace();
    updateCurrentLabel();
    updateToggleButton();
    scheduleAutoAdvance();
})();
