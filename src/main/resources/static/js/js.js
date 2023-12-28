

let body = document.body;

let profile = document.querySelector('.header .flex .profile');

document.querySelector('#user-btn').onclick = () =>{
   profile.classList.toggle('active');
   search.classList.remove('active');
}

let search = document.querySelector('.header .flex .search-form');

document.querySelector('#search-btn').onclick = () =>{
   search.classList.toggle('active');
   profile.classList.remove('active');
}

let sideBar = document.querySelector('.side-bar');

document.querySelector('#menu-btn').onclick = () =>{
   sideBar.classList.toggle('active');
   body.classList.toggle('active');
}

document.querySelector('.side-bar .close-side-bar').onclick = () =>{
   sideBar.classList.remove('active');
   body.classList.remove('active');
}

window.onscroll = () =>{
   profile.classList.remove('active');
   search.classList.remove('active');

   if(window.innerWidth < 1200){
      sideBar.classList.remove('active');
      body.classList.remove('active');
   }
}

let log = document.getElementById(log);

if(log<1){
   log.classList.add('fadeOut');
}

let contact = document.getElementById(contact);

if(contact<1){
    contact.add('fadeOut');
}


function validate_password() {

   var pass = document.getElementById('pass').value;
   var confirm_pass = document.getElementById('confirm_pass').value;
   if (pass != confirm_pass) {
      document.getElementById('wrong_pass_alert').style.color = 'red';
      document.getElementById('wrong_pass_alert').innerHTML
          = '☒ Use same password';
      document.getElementById('create').disabled = true;
      document.getElementById('create').style.opacity = (0.4);
   } else {
      document.getElementById('wrong_pass_alert').style.color = 'green';
      document.getElementById('wrong_pass_alert').innerHTML =
          '🗹 Password Matched';
      document.getElementById('create').disabled = false;
      document.getElementById('create').style.opacity = (1);
   }
}

