const shareBox = document.querySelector("[data-result-share]");

if (shareBox) {
    const nativeButton = shareBox.querySelector("[data-share-native]");
    const copyButton = shareBox.querySelector("[data-share-copy]");
    const whatsappLink = shareBox.querySelector("[data-share-whatsapp]");
    const status = shareBox.querySelector("[data-share-status]");
    const area = shareBox.dataset.shareArea || "uma area de tecnologia";
    const score = shareBox.dataset.shareScore || "";
    const quizUrl = new URL(shareBox.dataset.shareQuizUrl || "/quiz", window.location.origin).toString();
    const scoreText = score ? `, com ${score}% de compatibilidade` : "";
    const shareText = `Meu resultado no Rota TI foi ${area}${scoreText}. Faca o teste tambem: ${quizUrl}`;

    if (whatsappLink) {
        whatsappLink.href = `https://wa.me/?text=${encodeURIComponent(shareText)}`;
    }

    function setStatus(message) {
        if (!status) {
            return;
        }
        status.textContent = message;
        window.setTimeout(() => {
            status.textContent = "";
        }, 3200);
    }

    async function copyShareText() {
        try {
            await navigator.clipboard.writeText(shareText);
            setStatus("Texto copiado.");
        } catch (error) {
            setStatus("Nao foi possivel copiar automaticamente.");
        }
    }

    nativeButton?.addEventListener("click", async () => {
        if (navigator.share) {
            try {
                await navigator.share({
                    title: "Meu resultado no Rota TI",
                    text: shareText,
                    url: quizUrl
                });
                setStatus("Compartilhamento aberto.");
                return;
            } catch (error) {
                if (error.name === "AbortError") {
                    return;
                }
            }
        }

        await copyShareText();
    });

    copyButton?.addEventListener("click", copyShareText);
}
