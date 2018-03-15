	var isBold = false;
	var isItalic = false;
	var isUnderlined = false;
	
	window.onload = function(){
		var iframe = document.getElementById('myPage');
		var innerDoc = iframe.contentDocument || iframe.contentWindow.document;
		var body = innerDoc.body;
		var element;
		body.onmousemove = function showCoords(event) {
		    var x = event.clientX;
		    var y = event.clientY;
		    var coor = "X coords: " + x + ", Y coords: " + y;
		    element = innerDoc.elementFromPoint(x,y);
		    //modification prototype
		    element.ondblclick=function(){
		    	
		    	if(element.id!="modifier"){
		    		//sync style-button styles
			    	checkUnderline(element.style.textDecoration);
			    	
			    	
			    	var inputBox = innerDoc.createElement("textarea");
			    	//inputBox.setAttribute("type","text");
			    	//inputBox.setAttribute("value",element.textContent);
			    	inputBox.textContent = element.textContent;
			    	inputBox.length = element.length;
			    	inputBox.id="modifier";
			    	//copy text style
			    	//inputBox.style.color = element.style.color;
			    	//inputBox.style.fontSize = element.style.fontSize;
			    	copyTextStyle(element,inputBox);
			    	
			    	
			    	var parent = element.parentElement
			    	parent.removeChild(element); //need to change here
			    	parent.appendChild(inputBox);//need to change here
			    	
			    	//need to change enter key
			    	inputBox.onkeypress=function(event){
			    		if(event.keyCode==13){
			    			var newText = innerDoc.createElement("div");
			    			newText.textContent = inputBox.value;
			    			copyTextStyle(inputBox,newText);
			    			parent.removeChild(inputBox);//need to change here
			    			parent.appendChild(newText);//need to change here
			    			
			    			//change style-button style to normal
			    			checkUnderline("none");
			    		}	
			    	}
			    	
		    	}
		    }
		   // element.style.backgroundColor = "#C0C0C0";
		    document.getElementById("demo").innerHTML = "";
		    document.getElementById("demo").innerHTML +=element.textContent;
		}
		//for testing purpose
		body.onmouseout = function clearCoor() {
			//element.style.backgroundColor = "white";
		    document.getElementById("demo").innerHTML = "";    
		}
		
		//setting up bold button function
		var boldCell = document.getElementById("bold-cell");
		boldCell.addEventListener("click",function(){
			isBold = !isBold;
			if(isBold){
				boldCell.style.backgroundColor = "#A0A0A0";				
			}
			else{
				boldCell.style.backgroundColor = "transparent";
			}
		});
		
		//setting up italic button funtion
		var italicCell = document.getElementById("italic-cell");
		italicCell.addEventListener("click",function(){
			isItalic = !isItalic;
			if(isItalic){
				italicCell.style.backgroundColor = "#A0A0A0";
			}
			else{
				italicCell.style.backgroundColor = "transparent";
			}
		});
		
		var underlineCell = document.getElementById("underline-cell");
		underlineCell.addEventListener("click",function(){
			isUnderlined = !isUnderlined;
			var inputBox = innerDoc.getElementById("modifier");
			if(isUnderlined){
				underlineCell.style.backgroundColor="#A0A0A0";
				if(inputBox!=null){
					inputBox.style.textDecoration = "underline";
				}
			}
			else{
				underlineCell.style.backgroundColor="transparent";
				if(inputBox!=null){
					inputBox.style.textDecoration = "none";
				}
			}
		})
		
		
		//set up add link button function
		var addLinkButton = document.getElementById('add-link');
		addLinkButton.addEventListener("click",function(){
			popup("link")
		});
		addLinkButton.onmouseover=function(){
			addLinkButton.style.backgroundColor= "#A0A0A0";
		};
		addLinkButton.onmouseout=function(){
			addLinkButton.style.backgroundColor="transparent";
		};
		
		//set up add image button function
		var addImageButton = document.getElementById('add-image');
		addImageButton.addEventListener("click",function(){
			popup("image")
		});
		addImageButton.onmouseover=function(){
			addImageButton.style.backgroundColor="#A0A0A0";
		}
		addImageButton.onmouseout=function(){
			addImageButton.style.backgroundColor="transparent";
		}
		
	}
	
	function popup(type){
		var popup = document.getElementById("popup");
		popup.style.display = "block";
		var title = document.getElementById("popup-title");
		var inputBox = document.getElementById("popup-input");
		var addButton = document.getElementById("submit-button");
		if(type=="image"){
			title.innerHTML = "Add image";
			inputBox.placeholder="image url";
		}
		if(type=="link"){
			title.innerHTML = "Add link";
			inputBox.placeholder="link url";
		}
		
		addButton.addEventListener("click",function(){
			popup.style.display="none";
		});
		var quit = document.getElementById("popup-quit");
		quit.onmouseover=function(){
			quit.style.backgroundColor= "#E0E0E0";
		}
		quit.onmouseout=function(){
			quit.style.backgroundColor="transparent";
		}
		quit.onclick=function(){
			popup.style.display="none";
		}
	}
	
	function checkUnderline(style){
		var underlineCell = document.getElementById('underline-cell')
		if(style=="underline"){
			isUnderlined=true;
			underlineCell.style.backgroundColor = "#A0A0A0";
		}
		else{
			isUnderlinded = false;
			underlineCell.style.backgroundColor = "transparent";
		}
	}
	
	function copyTextStyle(src,des){
		des.style.color = src.style.color;
		des.style.fontSize = src.style.fontSize;
		des.style.fontWeight = src.style.fontWeight;
		des.style.fontStyle = src.style.fontStyle;
		des.style.textDecoration = src.style.textDecoration;
	}
	
	