const roadmapRoot = document.querySelector("[data-roadmap]");

if (roadmapRoot) {
    const slug = roadmapRoot.dataset.roadmapSlug || "rota";
    const storageKey = `rota-ti-roadmap-progress-v1:${slug}`;
    const checkboxes = Array.from(roadmapRoot.querySelectorAll("[data-roadmap-check]"));
    const completedOutput = roadmapRoot.querySelector("[data-roadmap-completed]");
    const percentOutput = roadmapRoot.querySelector("[data-roadmap-percent]");
    const progress = roadmapRoot.querySelector("[data-roadmap-progress]");
    const progressBar = roadmapRoot.querySelector("[data-roadmap-progress-bar]");
    const status = roadmapRoot.querySelector("[data-roadmap-status]");
    const resetButton = roadmapRoot.querySelector("[data-roadmap-reset]");

    function readProgress() {
        try {
            const stored = JSON.parse(window.localStorage.getItem(storageKey) || "[]");
            return new Set(Array.isArray(stored) ? stored : []);
        } catch (error) {
            return new Set();
        }
    }

    function writeProgress() {
        const completed = checkboxes
            .filter((checkbox) => checkbox.checked)
            .map((checkbox) => checkbox.dataset.roadmapCheck);

        try {
            window.localStorage.setItem(storageKey, JSON.stringify(completed));
            return true;
        } catch (error) {
            setStatus("Seu navegador nao permitiu salvar o progresso.");
            return false;
        }
    }

    function setStatus(message) {
        if (status) {
            status.textContent = message;
        }
    }

    function updateStage(stage) {
        const stageChecks = Array.from(stage.querySelectorAll("[data-roadmap-check]"));
        const stageCompleted = stageChecks.filter((checkbox) => checkbox.checked).length;
        const stageOutput = stage.querySelector("[data-stage-completed]");

        if (stageOutput) {
            stageOutput.textContent = String(stageCompleted);
        }

        stage.classList.toggle("is-complete", stageCompleted === stageChecks.length);
    }

    function updateProgress() {
        const completed = checkboxes.filter((checkbox) => checkbox.checked).length;
        const total = checkboxes.length;
        const percent = total === 0 ? 0 : Math.round((completed / total) * 100);

        if (completedOutput) {
            completedOutput.textContent = String(completed);
        }
        if (percentOutput) {
            percentOutput.textContent = `${percent}%`;
        }
        if (progress) {
            progress.setAttribute("aria-valuenow", String(completed));
        }
        if (progressBar) {
            progressBar.style.width = `${percent}%`;
        }

        roadmapRoot.querySelectorAll("[data-roadmap-stage]").forEach(updateStage);
        roadmapRoot.classList.toggle("is-roadmap-complete", total > 0 && completed === total);
    }

    function openStageFromHash() {
        const hash = window.location.hash;
        if (!hash.startsWith("#etapa-")) {
            return;
        }

        const stage = document.querySelector(hash);
        const details = stage?.querySelector("details");
        if (details) {
            details.open = true;
        }
    }

    const storedProgress = readProgress();
    checkboxes.forEach((checkbox) => {
        checkbox.checked = storedProgress.has(checkbox.dataset.roadmapCheck);
    });
    updateProgress();
    openStageFromHash();

    roadmapRoot.addEventListener("change", (event) => {
        const checkbox = event.target.closest("[data-roadmap-check]");
        if (!checkbox) {
            return;
        }

        writeProgress();
        updateProgress();

        const completed = checkboxes.filter((item) => item.checked).length;
        setStatus(checkbox.checked
            ? `Marco concluido. Voce completou ${completed} de ${checkboxes.length}.`
            : "Marco desmarcado. Seu progresso foi atualizado.");
    });

    resetButton?.addEventListener("click", () => {
        const shouldReset = window.confirm("Deseja apagar todas as marcacoes deste roadmap neste navegador?");
        if (!shouldReset) {
            return;
        }

        checkboxes.forEach((checkbox) => {
            checkbox.checked = false;
        });
        try {
            window.localStorage.removeItem(storageKey);
        } catch (error) {
            // The visual reset still works when storage is unavailable.
        }
        updateProgress();
        setStatus("Progresso deste roadmap apagado.");
    });

    window.addEventListener("hashchange", openStageFromHash);
}
