 function showTooltip(event, title, text, imgSrc) {
            const tooltip = document.getElementById("tooltip");
            tooltip.innerHTML = `<strong>${title}</strong><br>${text}<br><img src="${imgSrc}">`;
            tooltip.style.display = "block";
            tooltip.style.left = event.pageX + 10 + "px";
            tooltip.style.top = event.pageY + 10 + "px";
        }

        function hideTooltip() {
            document.getElementById("tooltip").style.display = "none";
        }
