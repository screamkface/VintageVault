//hamburger menu
function toggleHamburgerMenu() {
  document.querySelector(".hamburger-menu").classList.toggle("ham-active");
}
document
  .querySelectorAll(".hamburger-menu-icon")
  .forEach((el) => el.addEventListener("click", toggleHamburgerMenu));
//account popup
function toggleAccountPopup() {
  let accountPopup = document.querySelector(".account-popup");
  if (accountPopup.style.display == "block") {
    accountPopup.style.display = "none";
  } else {
    accountPopup.style.display = "block";
  }
}
document
  .querySelector("div.icons-wrapper > div:nth-child(1) > svg")
  .addEventListener("click", toggleAccountPopup);
//auto close account popup
document.addEventListener("click", function (event) {
  let accountPopup = document.querySelector(".account-popup");
  let accountIcon = document.querySelector("div.icons-wrapper > div:nth-child(1) > svg");
  let isClickInside = accountPopup.contains(event.target);
  if (
    !isClickInside &&
    accountPopup.style.display == "block" &&
    !accountIcon.contains(event.target)
  ) {
    accountPopup.style.display = "none";
  }
});
