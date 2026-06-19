const questions = document.querySelectorAll(".question");
const submitButton = document.querySelector(".quiz-form button[type='submit']");

function updateProgress() {
    if (!submitButton) {
        return;
    }

    const answered = Array.from(questions).filter((question) => {
        return question.querySelector("input[type='radio']:checked");
    }).length;

    submitButton.textContent = answered === questions.length
        ? "Ver meu resultado"
        : `Responder perguntas (${answered}/${questions.length})`;
}

questions.forEach((question) => {
    question.addEventListener("change", updateProgress);
});

updateProgress();
