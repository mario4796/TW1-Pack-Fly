const themeToggle = document.getElementById('themeToggle');
const body = document.body;

// Establecer tema inicial desde localStorage o sistema
const savedTheme = localStorage.getItem('theme');
if (savedTheme) {
    body.setAttribute('data-bs-theme', savedTheme);
    updateIcon(savedTheme);
} else if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
    body.setAttribute('data-bs-theme', 'dark');
    updateIcon('dark');
}

themeToggle.addEventListener('click', () => {
    const currentTheme = body.getAttribute('data-bs-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    body.setAttribute('data-bs-theme', newTheme);
    localStorage.setItem('theme', newTheme);
    updateIcon(newTheme);
});

function updateIcon(theme) {
    themeToggle.innerHTML = theme === 'dark'
        ? '<i class="bi bi-sun"></i>'
        : '<i class="bi bi-moon"></i>';
}