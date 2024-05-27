let darkTheme = "/default/styles/dark.css";
let lightTheme = "/default/styles/light.css";

function change_theme() {
    var next_theme = localStorage.getItem("color-scheme");
    if (next_theme === "dark") {
        $('#color-scheme').attr("href", darkTheme);
        localStorage.setItem("color-scheme", "light");
    } else {
        $('#color-scheme').attr("href", lightTheme);
        localStorage.setItem("color-scheme", "dark");
    }
}

window.onload = () => {
    var next_theme = localStorage.getItem("color-scheme");
    if (next_theme === "dark") {
        $('#color-scheme').attr("href", lightTheme);
    } else {
        $('#color-scheme').attr("href", darkTheme);
    }
};
