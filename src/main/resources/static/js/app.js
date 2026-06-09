(() => {
    const toggle = document.querySelector("[data-password-toggle]");
    if (toggle) {
        const input = document.querySelector(toggle.dataset.target);
        const icon = toggle.querySelector("i");
        toggle.addEventListener("click", () => {
            const visible = input.type === "text";
            input.type = visible ? "password" : "text";
            toggle.setAttribute("aria-label", visible ? "Afficher le mot de passe" : "Masquer le mot de passe");
            toggle.setAttribute("title", visible ? "Afficher le mot de passe" : "Masquer le mot de passe");
            icon.className = visible ? "bi bi-eye" : "bi bi-eye-slash";
        });
    }

    const path = window.location.pathname;
    document.querySelectorAll(".app-nav-link").forEach((link) => {
        const href = link.getAttribute("href");
        const active = href === "/" ? path === "/" : path.startsWith(href);
        if (active) {
            link.classList.add("active");
        }
    });
})();
