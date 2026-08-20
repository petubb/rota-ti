document.addEventListener("DOMContentLoaded", () => {
    const explorer = document.querySelector("[data-area-explorer]");
    if (!explorer) {
        return;
    }

    const cards = Array.from(explorer.querySelectorAll("[data-area-card]"));
    const filters = Array.from(explorer.querySelectorAll("[data-area-filter]"));
    const search = explorer.querySelector("[data-area-search]");
    const clearSearch = explorer.querySelector("[data-area-search-clear]");
    const searchSuggestions = explorer.querySelector("[data-area-search-suggestions]");
    const suggestionButtons = Array.from(explorer.querySelectorAll("[data-area-search-suggestion]"));
    const resetButton = explorer.querySelector("[data-area-reset]");
    const emptyState = explorer.querySelector("[data-area-empty]");
    const resultCount = explorer.querySelector("[data-area-results-count]");
    let activeFilter = "todos";

    function setSuggestionsOpen(open) {
        if (!search || !searchSuggestions) {
            return;
        }

        searchSuggestions.hidden = !open;
        search.setAttribute("aria-expanded", String(open));
    }

    function normalize(value) {
        return value
                .normalize("NFD")
                .replace(/[\u0300-\u036f]/g, "")
                .toLowerCase()
                .trim();
    }

    function updateResults() {
        const term = normalize(search?.value || "");
        let visible = 0;

        cards.forEach((card) => {
            const categories = (card.dataset.categories || "").split(" ");
            const matchesFilter = activeFilter === "todos" || categories.includes(activeFilter);
            const matchesSearch = !term || normalize(card.textContent).includes(term);
            const show = matchesFilter && matchesSearch;

            card.hidden = !show;
            if (show) {
                visible += 1;
            }
        });

        if (resultCount) {
            resultCount.textContent = `${visible} ${visible === 1 ? "rota encontrada" : "rotas encontradas"}`;
        }
        if (emptyState) {
            emptyState.hidden = visible !== 0;
        }
        if (clearSearch) {
            clearSearch.hidden = !search?.value;
        }
    }

    function selectFilter(filter) {
        activeFilter = filter;
        filters.forEach((button) => {
            const selected = button.dataset.areaFilter === filter;
            button.classList.toggle("is-active", selected);
            button.setAttribute("aria-pressed", String(selected));
        });
        updateResults();
    }

    function resetFilters() {
        if (search) {
            search.value = "";
        }
        selectFilter("todos");
        search?.focus();
    }

    filters.forEach((button) => {
        button.addEventListener("click", () => selectFilter(button.dataset.areaFilter));
    });

    search?.addEventListener("focus", () => setSuggestionsOpen(true));
    search?.addEventListener("input", () => {
        updateResults();
        setSuggestionsOpen(true);
    });
    search?.addEventListener("keydown", (event) => {
        if (event.key === "Escape") {
            if (search.value) {
                search.value = "";
                updateResults();
            } else {
                setSuggestionsOpen(false);
                search.blur();
            }
        }
    });

    suggestionButtons.forEach((button) => {
        button.addEventListener("click", () => {
            search.value = button.dataset.areaSearchSuggestion || button.textContent.trim();
            updateResults();
            search.focus({ preventScroll: true });
            setSuggestionsOpen(false);
        });
    });

    document.addEventListener("click", (event) => {
        if (!event.target.closest(".area-search-field")) {
            setSuggestionsOpen(false);
        }
    });

    clearSearch?.addEventListener("click", () => {
        search.value = "";
        updateResults();
        search.focus();
    });

    resetButton?.addEventListener("click", resetFilters);
    updateResults();
});
