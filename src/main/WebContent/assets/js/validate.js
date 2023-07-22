function validateField(elem, errorElem, errorMsg){
	
	if(!elem.checkValidity()){
		
		errorElem.textContent = errorMsg;
		errorElem.style.display = "flex";
	
	}else{
	
		errorElem.style.display = "none";
	
	}
}