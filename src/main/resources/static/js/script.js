console.log("Script loaded");

// Initial theme setup
let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
  // Set the initial theme when the DOM content is loaded
  changePageTheme(currentTheme, "");

  // Set the listener to change theme button
  const changeThemeButton = document.querySelector("#theme_change_button");
  
  if (changeThemeButton) {
    changeThemeButton.addEventListener("click", (event) => {
      let oldTheme = currentTheme;
      console.log("Change theme button clicked");

      // Toggle between light and dark themes
      currentTheme = currentTheme === "dark" ? "light" : "dark";
      
      console.log("New theme: " + currentTheme);

      // Change the page theme
      changePageTheme(currentTheme, oldTheme);
    });
  }
});

// Set theme to localStorage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

// Get theme from localStorage
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light"; // Default to light theme if none is set
}

// Change the current page theme
function changePageTheme(theme, oldTheme) {
  // Update localStorage with the new theme
  setTheme(theme);

  // Remove the old theme class from <html> tag if it exists
  if (oldTheme) {
    document.documentElement.classList.remove(oldTheme);
  }

  // Add the new theme class to <html> tag
  document.documentElement.classList.add(theme);

  // Change the text of the button based on the current theme
  const themeButtonText = theme === "light" ? "Dark" : "Light";
  document.querySelector("#theme_change_button span").textContent = themeButtonText;
}
