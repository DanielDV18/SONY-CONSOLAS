const nombre = document.getElementById("nombre")
const email = document.getElementById("email")
const direccion = document.getElementById("direccion")
const contraseña = document.getElementById("password")
const form = document.getElementById("form")
const parrafo = document.getElementById("warnings")

form.addEventListener("submit", e => {
    e.preventDefault()
    let warnings = ""
    let entrar = false
    let regexEmail = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i

    if (nombre.value.length < 4) {
        warnings += `El nombre no es valido <br>`
        entrar = true
    }
    if (!regexEmail.test(email.value)) {
        warnings += `El Correo no es valido <br>`
        entrar = true
    }
    if (contraseña.value.length < 8) {
        warnings += `La Contraseña no es valida <br>`
        entrar = true
    }
    if (entrar) {
        parrafo.innerHTML = warnings
    }
})