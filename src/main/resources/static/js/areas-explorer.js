document.addEventListener("DOMContentLoaded", () => {
    const explorer = document.querySelector("[data-area-explorer]");
    if (!explorer) {
        return;
    }

    const cards = Array.from(explorer.querySelectorAll("[data-area-card]"));
    const filters = Array.from(explorer.querySelectorAll("[data-area-filter]"));
    const search = explorer.querySelector("[data-area-search]");
    const clearSearch = explorer.querySelector("[data-area-search-clear]");
    const resetButton = explorer.querySelector("[data-area-reset]");
    const emptyState = explorer.querySelector("[data-area-empty]");
    const resultCount = explorer.querySelector("[data-area-results-count]");
    let activeFilter = "todos";

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

    search?.addEventListener("input", updateResults);
    search?.addEventListener("keydown", (event) => {
        if (event.key === "Escape" && search.value) {
            search.value = "";
            updateResults();
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
