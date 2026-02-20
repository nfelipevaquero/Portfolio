// Función para verificar si una imagen está dentro del viewport
function checkImagesInView() {
    const images = document.querySelectorAll('img'); // Selecciona todas las imágenes
    const windowHeight = window.innerHeight; // Altura del viewport

    images.forEach((image) => {
        const imageTop = image.getBoundingClientRect().top; // Obtiene la posición superior de la imagen

        // Si la imagen está dentro del viewport
        if (imageTop < windowHeight * 0.8) { // 80% del viewport visible
            image.classList.add('visible'); // Añade la clase para hacerla visible
        }
    });
}

// Llama a la función cuando se hace scroll o se carga la página
window.addEventListener('scroll', checkImagesInView);
window.addEventListener('load', checkImagesInView); // También al cargar la página



// Function to check if text elements are in view
function checkTextInView() {
    const texts = document.querySelectorAll('.texti'); // Select all elements with the class 'texti'
    const windowHeight = window.innerHeight; // Get the height of the viewport
  
    texts.forEach((text) => {
      const textTop = text.getBoundingClientRect().top; // Get the position of the text element relative to the viewport
  
      // If the text is within the viewport
      if (textTop < windowHeight * 0.8) { // Trigger when 80% of the viewport height
        text.classList.add('visible'); // Add the 'visible' class to animate the text
      }
    });
  }
  
  // Add event listeners to trigger the function on scroll and load
  window.addEventListener('scroll', checkTextInView);
  window.addEventListener('load', checkTextInView); // Also run on page load
