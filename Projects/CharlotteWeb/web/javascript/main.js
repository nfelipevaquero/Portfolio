let isHoveringSidebar = false;
          
            function openNav() {
              document.getElementById("mySidebar").style.width = "250px";
              document.getElementById("main").style.marginLeft = "250px";
            }
          
            function keepOpen() {
              isHoveringSidebar = true;
            }
          
            function closeNav() {
              isHoveringSidebar = false;
              setTimeout(() => {
                if (!isHoveringSidebar) {
                  document.getElementById("mySidebar").style.width = "0";
                  document.getElementById("main").style.marginLeft = "0";
                }
              }, 200);
            }