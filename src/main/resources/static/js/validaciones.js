//Esta es para nombres ^[a-zA-ZÁ-ÿ\s]{1,40}$

//rela para direccion ^\d{7,10}$

//regla correo ^[a-zA-Z0-9_+-.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$

//Regla password ^(?=.+\d)(?=.*[#$%&!@])(?=*[a-z])(?=*[A-Z]).{8,}$

const reglas = {
    textos: /^[a-zA-ZÁ-ÿ\s]{1,40}$/, //SOLO LETRAS
    numeros: /^\d{7,10}$/, //SOLO NÚMEROS
    email: /^[a-zA-Z0-9_+-.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/, //CORREOS
    password: /^(?=.*[a-z])(?=.*[A-Z]).{8,}$/ //PASSWORD
}

//Llamamos a los id de los fdormularios
const inputs={
    nombre: false,
    email: false,
    direccion: false,
    password: false
}

//acceder al formulario
let form = document.getElementById("frm-usuario")
let campos = document.querySelectorAll("#frm-usuario input")

//agregar listener de evento submit al formulario que se envia
form.addEventListener('submit', e => {
    e.preventDefault();//evitar que se envie el formulario para realizar las validaciones
})

const validarInput = (regla, input, grupo) => {
    if (regla.test(input.value)) {
        document.getElementById(`g-${grupo}`).classList.remove('error');
        document.getElementById(`g-${grupo}`).classList.add('success');
        document.querySelector(`#g-${grupo} i`).classList.add('fa-square-check');
        document.querySelector(`#g-${grupo} i`).classList.remove('fa-circle-exclamation');
        document.querySelector(`#g-${grupo} .msn-error`).classList.remove('msn-error-visible')
        inputs[grupo]=true;
    } else {
        document.getElementById(`g-${grupo}`).classList.add('error');
        document.getElementById(`g-${grupo}`).classList.remove('success');
        document.querySelector(`#g-${grupo} i`).classList.remove('fa-square-check');
        document.querySelector(`#g-${grupo} i`).classList.add('fa-circle-exclamation');
        document.querySelector(`#g-${grupo} .msn-error`).classList.add('msn-error-visible')
        inputs[grupo]=true;
    }
}

//validacion de campos
const validarcampos = (e) => {
    console.log("se genero un evento sobre el input" + e.target.name);
    switch (e.target.name) {
        case "nombre":
            validarInput(reglas.textos,e.target ,e.target.name);
            break;
        case "email":
            validarInput(reglas.email,e.target ,e.target.name);
            break;
        case "direccion":
            validarInput(reglas.password,e.target ,e.target.name);
            break;
        case "password":
            validarInput(reglas.password,e.target ,e.target.name);
            validarPassword();
            break; 
        default:
            alert("no se ha recibido ningun evento");
            break;
    }
}

//confirmacion de la contraseña
const validarPassword=()=>{
    const pass1 = document.getElementById('password');

    if (pass1.value === pass2.value){
        document.getElementById(`g-password`).classList.remove('error');
        document.getElementById(`g-password`).classList.add('success');
        document.querySelector(`#g-password .msn-error`).classList.remove('msn-error-visible');
        document.querySelector("#g-password i").classList.add('fa-circle-check')
        document.querySelector("#g-password i").classList.remove('fa-triangle-exclamation')
        inputs['password'] = true;
    } 
}

/*Validacion de campos*/ 
campos.forEach((campo) => {
    campo.addEventListener("keyup", validarcampos);
    campo.addEventListener("blur", validarcampos);
})

form.addEventListener('submit', e=>{
    e.preventDefault();
    const terminos=document.getElementById("password");
    if(inputs.nombre && inputs.email && inputs.direccion && inputs.password){
            alert("EL usuario ha sido registrado");
            form.reset()
            document.querySelectorAll('.success').forEach(icono=>{
                icono.classList.remove('success')
            })
    }
    else{
        document.querySelectorAll('.msn-error').forEach(icono=>{
            icono.classList.add('msn-error-visible')
        })
    }
})