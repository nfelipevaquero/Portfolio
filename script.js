document.addEventListener('DOMContentLoaded', () => {
    const mainContent = document.querySelector('.main-content');
    const sidebar = document.querySelector('.sidebar');
    
    // Animación de entrada suave
    [sidebar, mainContent].forEach((el, index) => {
        el.style.opacity = '0';
        el.style.transform = index === 0 ? 'translateX(-20px)' : 'translateX(20px)';
        el.style.transition = 'all 0.8s ease-out';
        
        setTimeout(() => {
            el.style.opacity = '1';
            el.style.transform = 'translateY(0)';
        }, 200 * index);
    });
});