document.addEventListener("DOMContentLoaded", function() {
    // 1. Cập nhật năm footer
    const yearSpan = document.getElementById('current-year');
    if(yearSpan) yearSpan.textContent = new Date().getFullYear();
    
    // 2. Mobile Menu
    const mobileMenuButton = document.getElementById('mobile-menu-button');
    const mobileMenu = document.getElementById('mobile-menu');
    if(mobileMenuButton && mobileMenu) {
        mobileMenuButton.addEventListener('click', () => mobileMenu.classList.toggle('hidden'));
    }

    // 3. Mobile Categories Accordion
    const mobileCategoriesButton = document.getElementById('mobile-categories-button');
    const mobileCategoriesDropdown = document.getElementById('mobile-categories-dropdown');
    if (mobileCategoriesButton && mobileCategoriesDropdown) {
        mobileCategoriesButton.addEventListener('click', () => {
            mobileCategoriesDropdown.classList.toggle('hidden');
        });
    }

    // 4. Hiệu ứng hoa rơi khi click chuột
    document.addEventListener('click', function (e) {
        // Không tạo hoa nếu bấm vào link, button, input
        if (e.target.closest('a, button, input, select, textarea')) return;
        
        for (let i = 0; i < 5; i++) {
            setTimeout(() => createPetal(e.clientX, e.clientY), i * 50);
        }
    });

    function createPetal(x, y) {
        const petal = document.createElement('span');
        const petals = ['🌸', '💮', '🌹', '🌺'];
        petal.textContent = petals[Math.floor(Math.random() * petals.length)];
        petal.classList.add('petal');
        
        const size = 1 + Math.random();
        const duration = 2 + Math.random() * 2;
        
        petal.style.fontSize = `${size}rem`;
        petal.style.setProperty('--fall-duration', `${duration}s`);
        
        const offsetX = (Math.random() - 0.5) * 50;
        const offsetY = (Math.random() - 0.5) * 50;
        
        petal.style.left = `${x + offsetX}px`;
        petal.style.top = `${y + offsetY}px`;
        
        document.body.appendChild(petal);
        petal.addEventListener('animationend', () => petal.remove());
    }
});