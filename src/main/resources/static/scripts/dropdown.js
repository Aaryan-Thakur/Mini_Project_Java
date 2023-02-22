const menu = ()=>{
    const menu=document.getElementById("dropmenu");
    console.log(menu.style)
    if(menu.style.display=="none"){
        menu.style.display="flex"
    }
    else{
        menu.style.display="none"
    }
}