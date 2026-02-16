document.addEventListener('DOMContentLoaded', () => {
    const sections = document.querySelectorAll('section');

    const appearanceOptions = {
        threshold: 0.1,
        rootMargin: "0px 0px -50px 0px"
    };

    const appearanceOnScroll = new IntersectionObserver(function(entries, appearanceOnScroll) {
        entries.forEach(entry => {
            if (!entry.isIntersecting) return;
            
            entry.target.style.opacity = "1";
            entry.target.style.transform = "translateY(0)";
            appearanceOnScroll.unobserve(entry.target);
        });
    }, appearanceOptions);

    sections.forEach(section => {
        section.style.transition = "all 0.8s ease-out";
        appearanceOnScroll.observe(section);
    });
});