(() => {
    const collator = new Intl.Collator("pt-BR", {
        numeric: true,
        sensitivity: "base"
    });

    document.querySelectorAll("[data-sortable-table]").forEach((table) => {
        const body = table.tBodies[0];
        if (!body) {
            return;
        }

        const originalOrder = new Map(
            Array.from(body.rows).map((row, index) => [row, index])
        );

        table.querySelectorAll("[data-sort-index]").forEach((button) => {
            button.addEventListener("click", () => {
                const columnIndex = Number(button.dataset.sortIndex);
                const numeric = button.dataset.sortType === "number";
                const header = button.closest("th");
                const descending = header.getAttribute("aria-sort") === "ascending";

                table.querySelectorAll("th[aria-sort]").forEach((item) => {
                    item.setAttribute("aria-sort", "none");
                });
                header.setAttribute("aria-sort", descending ? "descending" : "ascending");

                const rows = Array.from(body.rows);
                rows.sort((firstRow, secondRow) => {
                    const firstValue = cellValue(firstRow.cells[columnIndex]);
                    const secondValue = cellValue(secondRow.cells[columnIndex]);
                    let comparison;

                    if (numeric) {
                        comparison = numberValue(firstValue) - numberValue(secondValue);
                    } else {
                        comparison = collator.compare(firstValue, secondValue);
                    }

                    if (comparison === 0) {
                        comparison = originalOrder.get(firstRow) - originalOrder.get(secondRow);
                    }
                    return descending ? -comparison : comparison;
                });

                rows.forEach((row) => body.appendChild(row));
            });
        });
    });

    function cellValue(cell) {
        if (!cell) {
            return "";
        }
        return (cell.dataset.sortValue ?? cell.textContent).trim();
    }

    function numberValue(value) {
        const parsed = Number(value.replace(",", "."));
        return Number.isFinite(parsed) ? parsed : Number.NEGATIVE_INFINITY;
    }
})();
