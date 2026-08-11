const wizard = document.querySelector("[data-quiz-wizard]");

if (wizard) {
    const steps = Array.from(wizard.querySelectorAll("[data-quiz-step]"));
    const questionSteps = steps.filter((step) => step.hasAttribute("data-question-step"));
    const backButton = wizard.querySelector("[data-quiz-back]");
    const nextButton = wizard.querySelector("[data-quiz-next]");
    const submitButton = wizard.querySelector("[data-quiz-submit]");
    const progress = wizard.querySelector(".quiz-progress");
    const progressBar = wizard.querySelector("[data-progress-bar]");
    const progressCurrent = wizard.querySelector("[data-progress-current]");
    const stepLabel = wizard.querySelector("[data-step-label]");
    const stepError = wizard.querySelector("[data-step-error]");
    const schoolCombobox = wizard.querySelector("[data-school-combobox]");
    const schoolInput = wizard.querySelector("[data-school-input]");
    const schoolToggle = wizard.querySelector("[data-school-toggle]");
    const schoolOptionsList = wizard.querySelector("[data-school-options]");
    const otherSchoolField = wizard.querySelector("[data-other-school-field]");
    const otherSchoolInput = wizard.querySelector("[data-other-school-input]");
    const schoolOptions = schoolOptionsList
        ? Array.from(schoolOptionsList.querySelectorAll("[data-school-option]"))
        : [];
    const otherSchoolValue = schoolInput?.dataset.otherValue || "";
    const validSchoolValues = new Set(
        schoolOptions
            .map((option) => option.dataset.value)
            .filter((value) => value && value !== otherSchoolValue)
    );
    const stepPrefix = wizard.dataset.stepPrefix || "Pergunta";
    const reducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
    let currentStep = findInitialStep();
    let suppressNextSchoolOpen = false;

    document.body.classList.add("quiz-ready");

    function findInitialStep() {
        const profileHasError = wizard.querySelector(".quiz-profile-step .error");
        if (profileHasError) {
            return 0;
        }

        const globalError = wizard.querySelector(".quiz-global-error");
        if (globalError) {
            const firstUnanswered = questionSteps.findIndex((step) => {
                return !step.querySelector("input[type='radio']:checked");
            });
            return firstUnanswered >= 0 ? steps.indexOf(questionSteps[firstUnanswered]) : 0;
        }

        return 0;
    }

    function showStep(index, moveFocus = false) {
        currentStep = Math.max(0, Math.min(index, steps.length - 1));

        steps.forEach((step, stepIndex) => {
            const isActive = stepIndex === currentStep;
            step.classList.toggle("is-active", isActive);
            step.setAttribute("aria-hidden", String(!isActive));
        });

        const activeStep = steps[currentStep];
        const questionIndex = questionSteps.indexOf(activeStep);
        const questionNumber = questionIndex >= 0 ? questionIndex + 1 : 0;
        const percentage = questionSteps.length === 0
            ? 0
            : (questionNumber / questionSteps.length) * 100;
        const isLastStep = currentStep === steps.length - 1;

        progressBar.style.width = `${percentage}%`;
        progressCurrent.textContent = String(questionNumber);
        progress.setAttribute("aria-valuenow", String(questionNumber));
        stepLabel.textContent = activeStep.hasAttribute("data-profile-step")
            ? "Seu perfil"
            : `${stepPrefix} ${questionNumber}`;

        backButton.hidden = currentStep === 0;
        nextButton.hidden = isLastStep;
        submitButton.hidden = !isLastStep;
        clearStepError();

        if (moveFocus) {
            wizard.scrollIntoView({
                behavior: reducedMotion ? "auto" : "smooth",
                block: "start"
            });
            steps[currentStep].querySelector("h2, legend")?.focus({preventScroll: true});
        }
    }

    function validateStep() {
        const step = steps[currentStep];
        const requiredInputs = Array.from(step.querySelectorAll("input[required]"));
        let message = "";

        if (step.hasAttribute("data-profile-step")) {
            validateSchoolChoice();
            const invalidInput = requiredInputs.find((input) => !input.checkValidity());
            if (invalidInput) {
                message = invalidInput.validationMessage || "Preencha seu nome, idade e escola antes de continuar.";
                invalidInput.focus();
            }
        } else if (!step.querySelector("input[type='radio']:checked")) {
            message = "Escolha uma resposta para continuar.";
            step.querySelector("input[type='radio']")?.focus();
        }

        step.classList.toggle("has-error", Boolean(message));
        stepError.textContent = message;
        return !message;
    }

    function validateSchoolChoice() {
        if (!schoolInput) {
            return true;
        }

        schoolInput.setCustomValidity("");
        otherSchoolInput?.setCustomValidity("");

        const value = schoolInput.value.trim();
        if (!value) {
            return false;
        }

        if (value === otherSchoolValue) {
            if (!otherSchoolInput?.value.trim()) {
                otherSchoolInput?.setCustomValidity("Informe o nome da escola.");
                return false;
            }
            return true;
        }

        if (!validSchoolValues.has(value)) {
            schoolInput.setCustomValidity("Selecione uma escola estadual da lista ou escolha Outra escola.");
            return false;
        }

        return true;
    }

    function normalizeSearchText(value) {
        return value
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .toLowerCase()
            .trim();
    }

    function setSchoolOptionsExpanded(expanded) {
        if (!schoolInput || !schoolOptionsList) {
            return;
        }

        schoolOptionsList.hidden = !expanded;
        schoolInput.setAttribute("aria-expanded", String(expanded));
        schoolToggle?.setAttribute("aria-expanded", String(expanded));
    }

    function openSchoolOptions() {
        if (!schoolOptionsList || suppressNextSchoolOpen) {
            return;
        }

        filterSchoolOptions();
        setSchoolOptionsExpanded(true);
    }

    function closeSchoolOptions() {
        setSchoolOptionsExpanded(false);
    }

    function filterSchoolOptions() {
        if (!schoolInput || !schoolOptionsList) {
            return;
        }

        const query = normalizeSearchText(schoolInput.value);
        schoolOptions.forEach((option) => {
            const value = option.dataset.value || "";
            const text = normalizeSearchText(option.textContent || "");
            const isOtherSchool = value === otherSchoolValue;
            option.hidden = !isOtherSchool && Boolean(query) && !text.includes(query);
        });
    }

    function selectSchoolOption(option) {
        if (!schoolInput) {
            return;
        }

        schoolInput.value = option.dataset.value || "";
        schoolInput.setCustomValidity("");
        syncOtherSchoolField();
        closeSchoolOptions();
        clearStepError();

        if (schoolInput.value === otherSchoolValue) {
            otherSchoolInput?.focus();
        } else {
            suppressNextSchoolOpen = true;
            schoolInput.focus({preventScroll: true});
            window.setTimeout(() => {
                suppressNextSchoolOpen = false;
            }, 0);
        }
    }

    function firstVisibleSchoolOption() {
        return schoolOptions.find((option) => !option.hidden);
    }

    function focusSiblingSchoolOption(currentOption, direction) {
        const visibleOptions = schoolOptions.filter((option) => !option.hidden);
        const currentIndex = visibleOptions.indexOf(currentOption);
        if (currentIndex < 0 || visibleOptions.length === 0) {
            return;
        }

        const nextIndex = (currentIndex + direction + visibleOptions.length) % visibleOptions.length;
        visibleOptions[nextIndex].focus();
    }

    function syncOtherSchoolField() {
        if (!schoolInput || !otherSchoolField || !otherSchoolInput) {
            return;
        }

        const showOtherSchool = schoolInput.value.trim() === otherSchoolValue;
        otherSchoolField.hidden = !showOtherSchool;
        otherSchoolInput.required = showOtherSchool;

        if (!showOtherSchool) {
            otherSchoolInput.value = "";
            otherSchoolInput.setCustomValidity("");
        }
    }

    function clearStepError() {
        steps[currentStep]?.classList.remove("has-error");
        stepError.textContent = "";
    }

    nextButton.addEventListener("click", () => {
        if (validateStep()) {
            showStep(currentStep + 1, true);
        }
    });

    backButton.addEventListener("click", () => {
        showStep(currentStep - 1, true);
    });

    wizard.addEventListener("input", () => {
        schoolInput?.setCustomValidity("");
        filterSchoolOptions();
        syncOtherSchoolField();
        clearStepError();
    });
    wizard.addEventListener("change", () => {
        syncOtherSchoolField();
        clearStepError();
    });

    schoolInput?.addEventListener("focus", openSchoolOptions);
    schoolInput?.addEventListener("click", openSchoolOptions);

    schoolInput?.addEventListener("keydown", (event) => {
        if (event.key === "ArrowDown") {
            event.preventDefault();
            openSchoolOptions();
            firstVisibleSchoolOption()?.focus();
        }
        if (event.key === "Escape") {
            closeSchoolOptions();
        }
    });

    schoolToggle?.addEventListener("click", () => {
        if (schoolOptionsList?.hidden) {
            schoolInput?.focus();
            openSchoolOptions();
        } else {
            closeSchoolOptions();
        }
    });

    schoolOptions.forEach((option) => {
        option.addEventListener("click", () => selectSchoolOption(option));
        option.addEventListener("keydown", (event) => {
            if (event.key === "ArrowDown") {
                event.preventDefault();
                focusSiblingSchoolOption(option, 1);
            }
            if (event.key === "ArrowUp") {
                event.preventDefault();
                focusSiblingSchoolOption(option, -1);
            }
            if (event.key === "Escape") {
                closeSchoolOptions();
                schoolInput?.focus();
            }
        });
    });

    document.addEventListener("click", (event) => {
        if (!schoolCombobox?.contains(event.target)) {
            closeSchoolOptions();
        }
    });

    wizard.addEventListener("keydown", (event) => {
        if (
            event.key !== "Enter"
            || event.target.closest("[data-school-combobox]")
            || event.target.closest("button, a")
        ) {
            return;
        }

        event.preventDefault();
        if (currentStep < steps.length - 1) {
            nextButton.click();
        } else {
            submitButton.click();
        }
    });

    wizard.addEventListener("submit", (event) => {
        if (!validateStep()) {
            event.preventDefault();
        }
    });

    syncOtherSchoolField();
    showStep(currentStep);
}
