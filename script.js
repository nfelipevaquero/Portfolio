let highestZ = 100;

function toggleStart() {
    const menu = document.getElementById('start-menu');
    menu.style.display = menu.style.display === 'flex' ? 'none' : 'flex';
}

function focusWindow(id) {
    highestZ++;
    document.getElementById(id).style.zIndex = highestZ;
    document.getElementById('start-menu').style.display = 'none';
}

function openWindow(id) {
    const win = document.getElementById(id);
    const task = document.getElementById('task-' + id);
    win.style.display = 'flex';
    task.classList.add('visible');
    focusWindow(id);
}

function closeWindow(id) {
    document.getElementById(id).style.display = 'none';
    document.getElementById('task-' + id).classList.remove('visible');
}

function minimizeWindow(id) {
    document.getElementById(id).style.display = 'none';
}

function toggleMinimize(id) {
    const win = document.getElementById(id);
    if (win.style.display === 'none') {
        win.style.display = 'flex';
        focusWindow(id);
    } else {
        win.style.display = 'none';
    }
}

function maximizeWindow(id) {
    document.getElementById(id).classList.toggle('maximized');
}

// Reloj
function updateClock() {
    const now = new Date();
    const time = now.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
    document.getElementById('clock').innerText = time;
}
setInterval(updateClock, 1000);
updateClock();

// Cerrar menú al hacer clic en el escritorio
document.querySelector('.desktop').onclick = (e) => {
    if (e.target.classList.contains('desktop')) {
        document.getElementById('start-menu').style.display = 'none';
    }
}