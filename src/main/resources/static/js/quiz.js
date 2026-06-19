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
    const reducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
    let currentStep = findInitialStep();

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
            return firstUnanswered >= 0 ? firstUnanswered + 1 : 0;
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

        const questionNumber = Math.max(0, currentStep);
        const percentage = questionSteps.length === 0
            ? 0
            : (questionNumber / questionSteps.length) * 100;
        const isLastStep = currentStep === steps.length - 1;

        progressBar.style.width = `${percentage}%`;
        progressCurrent.textContent = String(questionNumber);
        progress.setAttribute("aria-valuenow", String(questionNumber));
        stepLabel.textContent = currentStep === 0
            ? "Seu perfil"
            : `Pergunta ${questionNumber}`;

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
            const invalidInput = requiredInputs.find((input) => !input.checkValidity());
            if (invalidInput) {
                message = "Preencha sua idade e sua escola antes de continuar.";
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

    wizard.addEventListener("input", clearStepError);
    wizard.addEventListener("change", clearStepError);

    wizard.addEventListener("keydown", (event) => {
        if (event.key !== "Enter" || event.target.matches("input[type='radio']")) {
            return;
        }

        if (currentStep < steps.length - 1) {
            event.preventDefault();
            nextButton.click();
        }
    });

    wizard.addEventListener("submit", (event) => {
        if (!validateStep()) {
            event.preventDefault();
        }
    });

    showStep(currentStep);
}
