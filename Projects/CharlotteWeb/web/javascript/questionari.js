document.addEventListener("DOMContentLoaded", function () {
    const continenteSelect = document.getElementById("continente");
    const paisSelect = document.getElementById("pais");
    const totalContainer = document.createElement("p"); // Crear el elemento para el total
    const formulario = document.getElementById("purchase-form");

// Estructura de países por continente
const paisesPorContinente = {
    america: ["Argentina", "Bahamas", "Barbados", "Belize", "Bolivia", "Brazil", "Canada", "Chile", "Colombia", "Costa Rica", "Cuba", "Dominica", "Ecuador", "El Salvador", "United States", "Grenada", "Guatemala", "Guyana", "Haiti", "Honduras", "Jamaica", "Mexico", "Nicaragua", "Panama", "Paraguay", "Peru", "Dominican Republic", "Saint Kitts and Nevis", "Saint Vincent and the Grenadines", "Saint Lucia", "Suriname", "Trinidad and Tobago", "Uruguay", "Venezuela"],
    europa: ["Albania", "Germany", "Andorra", "Armenia", "Austria", "Belgium", "Belarus", "Bosnia and Herzegovina", "Bulgaria", "Czech Republic", "Cyprus", "Croatia", "Denmark", "Slovakia", "Slovenia", "Spain", "Estonia", "Finland", "France", "Georgia", "Greece", "Hungary", "Ireland", "Iceland", "Italy", "Kazakhstan", "Kosovo", "Latvia", "Liechtenstein", "Lithuania", "Luxembourg", "Malta", "Moldova", "Monaco", "Montenegro", "Netherlands", "Poland", "Portugal", "United Kingdom", "North Macedonia", "Romania", "Russia", "San Marino", "Serbia", "Sweden", "Switzerland", "Ukraine"],
    asia: ["Afghanistan", "Saudi Arabia", "Armenia", "Azerbaijan", "Bangladesh", "Bahrain", "Myanmar", "Bhutan", "Brunei", "Cambodia", "China", "North Korea", "South Korea", "United Arab Emirates", "Philippines", "Georgia", "India", "Indonesia", "Iraq", "Iran", "Israel", "Japan", "Jordan", "Kazakhstan", "Kuwait", "Kyrgyzstan", "Laos", "Lebanon", "Malaysia", "Maldives", "Mongolia", "Nepal", "Oman", "Pakistan"],
    africa: ["Angola", "Algeria", "Botswana", "Burkina Faso", "Burundi", "Cape Verde", "Cameroon", "Comoros", "Congo", "Democratic Republic of the Congo", "Ivory Coast", "Djibouti", "Egypt", "Eritrea", "Eswatini", "Ethiopia", "Gabon", "Gambia", "Ghana", "Guinea", "Guinea-Bissau", "Kenya", "Lesotho", "Liberia", "Libya", "Madagascar", "Malawi", "Mali", "Mauritius", "Mauritania", "Morocco", "Mozambique", "Namibia", "Niger", "Nigeria", "Rwanda", "Western Sahara", "Sao Tome and Principe", "Senegal", "Seychelles", "Sierra Leone", "Somalia", "South Africa", "Sudan", "Tanzania", "Togo", "Tunisia", "Uganda", "Zambia", "Zimbabwe"],
    oceania: ["Australia", "Fiji", "Marshall Islands", "Micronesia", "Solomon Islands", "Kiribati", "Nauru", "New Zealand", "Palau", "Papua New Guinea", "Samoa", "Tonga", "Tuvalu", "Vanuatu"]
};


    // Actualizar lista de países según continente seleccionado
    continenteSelect.addEventListener("change", function () {
        const continente = continenteSelect.value;
        paisSelect.innerHTML = "<option value=''>You must select your continent first!</option>";

        if (continente && paisesPorContinente[continente]) {
            paisesPorContinente[continente].forEach(pais => {
                const option = document.createElement("option");
                option.value = pais.toLowerCase();
                option.textContent = pais;
                paisSelect.appendChild(option);
            });
        }
    });

    // Obtener el total del carrito desde localStorage
    const carrito = JSON.parse(localStorage.getItem("carrito")) || [];

    // Calcular el total de la compra
    let totalCarrito = 0;
    if (carrito.length > 0) {
        totalCarrito = carrito.reduce((total, producto) => total + producto.precio, 0);
    }

    // Mostrar el total en el formulario
    totalContainer.textContent = `Total : $${totalCarrito.toFixed(2)}`;
    totalContainer.style.fontSize = "18px";
    totalContainer.style.fontWeight = "bold";
    totalContainer.style.marginTop = "10px";

    // Insertar el total antes del botón de compra
    formulario.appendChild(totalContainer);
    
    
});
