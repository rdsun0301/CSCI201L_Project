	var isUnderlined = false;
	var isEditing = false;
	var sel;
	var range;
	var iframe;

	function load(){
		loadPage();
		iframe = document.getElementById('myPage');
		var innerDoc = iframe.contentDocument || iframe.contentWindow.document;
		console.log(iframe.contentWindow.document);
		//test
		innerDoc.designMode="on";
		//innerDoc.execCommand("enableObjectResizing", false, "true");
		var body = innerDoc.body;
		
		/*delete for test
		sel = innerDoc.getSelection();
	    if (sel.rangeCount && sel.getRangeAt) {
	        range = sel.getRangeAt(0);
	    }
	    if (range) {
	        sel.removeAllRanges();
	        sel.addRange(range);
	    }
		 */
		
	    //setting up undo button
	    var undoCell = document.getElementById("undo-cell");
	    undoCell.onmouseover=function(){
	    	undoCell.style.backgroundColor = "#A0A0A0";	
	    }
	    undoCell.onmouseout=function(){
	    	undoCell.style.backgroundColor = "transparent";	
	    }
	    undoCell.onclick=function(){
	    	 innerDoc.execCommand("undo", false,"");
	    }
	    
	    //setting up redo button
	    var redoCell = document.getElementById("redo-cell");
	    redoCell.onmouseover=function(){
	    	redoCell.style.backgroundColor = "#A0A0A0";	
	    }
	    redoCell.onmouseout=function(){
	    	redoCell.style.backgroundColor = "transparent";	
	    }
	    redoCell.onclick=function(){
	    	 innerDoc.execCommand("redo", false,"");
	    }
	    
		//setting up font family menu
		var fontFamilyMenu = document.getElementById("font-family-menu");
		fontFamilyMenu.onchange = function(){
		    innerDoc.execCommand("fontName", false, fontFamilyMenu.value.replace(/-/g," "));
		}
		
		
		//setting up font size menu
		var fontSizeMenu = document.getElementById("font-size-menu");
		fontSizeMenu.onchange = function(){
			innerDoc.execCommand("fontSize", false, (fontSizeMenu.value-8)/4);
		}
		
		
		//setting up bold button function
		var boldCell = document.getElementById("bold-cell");
		boldCell.addEventListener("click",function(){
			innerDoc.execCommand("bold",false,"");

		});
		
		boldCell.onmouseover=function(){
			boldCell.style.backgroundColor = "#A0A0A0";	
		}
		boldCell.onmouseout=function(){
			boldCell.style.backgroundColor = "transparent";	
		}
		
		//setting up italic button function
		var italicCell = document.getElementById("italic-cell");
		italicCell.addEventListener("click",function(){
			innerDoc.execCommand("italic",false,"");
		});
		italicCell.onmouseover=function(){
			italicCell.style.backgroundColor = "#A0A0A0";	
		}
		italicCell.onmouseout=function(){
			italicCell.style.backgroundColor = "transparent";	
		}
		
		
		//setting up underline button function
		var underlineCell = document.getElementById("underline-cell");
		underlineCell.addEventListener("click",function(){
			innerDoc.execCommand("underline",false,"");
		})
		underlineCell.onmouseover=function(){
			underlineCell.style.backgroundColor = "#A0A0A0";	
		}
		underlineCell.onmouseout=function(){
			underlineCell.style.backgroundColor = "transparent";	
		}
		
		//setting up font color button function
		var menuCells = document.getElementsByClassName("color-cell");
		for(var i=0;i<menuCells.length;i++){			
			menuCells[i].addEventListener("mouseover",function(){
				this.style.backgroundColor="#808080";
			});
			menuCells[i].addEventListener("mouseout",function(){
				this.style.backgroundColor="transparent";
			});
			menuCells[i].addEventListener("click",function(){
				innerDoc.execCommand("foreColor",false,this.firstElementChild.style.backgroundColor);
				checkFontColor(this.firstElementChild.style.backgroundColor);
			})
			
		}
		
		var fontColorCell = document.getElementById("font-color-cell");
		var fontColorMenu = document.getElementById("font-color-menu");
		fontColorCell.onmouseover = function(){
			this.style.backgroundColor="#A0A0A0";
			//var rect = fontColorCell.getBoundingClientRect();
			//fontColorMenu.style.left = rect.left-180+"px";
			//fontColorMenu.style.top = rect.top-58+"px";
			fontColorMenu.style.display = "block";
			fontColorMenu.onmouseout = function(){
				fontColorMenu.style.display = "none";
				fontColorCell.style.backgroundColor="#F5F5F5";
			}	
		}
		fontColorCell.onmouseout = function(){
			fontColorMenu.style.display = "none";
			fontColorCell.style.backgroundColor="#F5F5F5";
		}	
		
		//set up align left button
		var alignLeftCell = document.getElementById("align-left-cell");
		alignLeftCell.onmouseover=function(){
			alignLeftCell.style.backgroundColor="#A0A0A0";
		}
		alignLeftCell.onmouseout=function(){
			alignLeftCell.style.backgroundColor = "transparent";	
		}
		alignLeftCell.onclick=function(){
			innerDoc.execCommand("justifyLeft",false,"");
		}
		
		//set up align center button
		var alignCenterCell = document.getElementById("align-center-cell");
		alignCenterCell.onmouseover=function(){
			alignCenterCell.style.backgroundColor="#A0A0A0";
		}
		alignCenterCell.onmouseout=function(){
			alignCenterCell.style.backgroundColor = "transparent";	
		}
		alignCenterCell.onclick=function(){
			innerDoc.execCommand("justifyCenter",false,"");
		}
		
		//set up align right button
		var alignRightCell = document.getElementById("align-right-cell");
		alignRightCell.onmouseover=function(){
			alignRightCell.style.backgroundColor="#A0A0A0";
		}
		alignRightCell.onmouseout=function(){
			alignRightCell.style.backgroundColor = "transparent";	
		}
		alignRightCell.onclick=function(){
			innerDoc.execCommand("justifyRight",false,"");
		}
		
		//set adding unordered list button
		var ulCell = document.getElementById("unordered-list-cell");
		ulCell.onmouseover=function(){
			ulCell.style.backgroundColor = "#A0A0A0";
		}
		ulCell.onmouseout = function(){
			ulCell.style.backgroundColor = "transparent";
		}
		ulCell.onclick = function(){
			innerDoc.execCommand("insertUnorderedList",false,"");
		}
		
		//set adding unordered list button
		var olCell = document.getElementById("ordered-list-cell");
		olCell.onmouseover=function(){
			olCell.style.backgroundColor = "#A0A0A0";
		}
		olCell.onmouseout = function(){
			olCell.style.backgroundColor = "transparent";
		}
		olCell.onclick = function(){
			innerDoc.execCommand("insertOrderedList",false,"");
		}
	
		
		//set up add link button function
		var addLinkButton = document.getElementById('add-link');
		addLinkButton.addEventListener("click",function(){
			popup("link",innerDoc)
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
			popup("image",innerDoc)
		});
		addImageButton.onmouseover=function(){
			addImageButton.style.backgroundColor="#A0A0A0";
		}
		addImageButton.onmouseout=function(){
			addImageButton.style.backgroundColor="transparent";
		}
		
		//test
		var addTableCell = document.getElementById("add-table")
		addTableCell.onmouseover = function(){
			addTableCell.style.backgroundColor = "#A0A0A0";
		}
		addTableCell.onmouseout = function(){
			addTableCell.style.backgroundColor = "transparent";
		}
		addTableCell.onclick = function(){
			var table = innerDoc.createElement("table");
			for(var i = 0; i < 2; i++){
				var row = innerDoc.createElement("tr");
				for(var j = 0; j < 2; j ++){
					var tableCell = innerDoc.createElement("td");
					tableCell.style.border = "1px solid black";
					tableCell.style.minWidth = "20px";
					row.appendChild(tableCell);
				}
				table.appendChild(row);
			}
			table.style.borderCollapse = "collapse";
			innerDoc.execCommand("insertHTML",false,table.outerHTML);
		}
		
		//still in test
		/*
		var mypages = document.getElementById("my-pages-directory").getElementsByClassName("mypage-dir");
		//alert(mypages.length);
		for(var i = 0; i < mypages.length;i++){
			mypages[i].onmouseover = function(){
				this.style.backgroundColor = "#F5F5F5";
			}
			mypages[i].onmouseout = function(){
				this.style.backgroundColor = "transparent";
			}
			mypages[i].onclick = function(){

				iframe.src = this.textContent;
				
				var newDoc = iframe.contentDocument || iframe.contentWindow.document;
				console.log(newDoc.body.innerHTML);

			}
		}
		
		*/
	}
	
	
	function popup(type,innerDoc){
		var popup = document.getElementById("popup");
		var popupForm = document.getElementById("popup-form")
		popup.style.display = "block";
		var title = document.getElementById("popup-title");
		var userInput = document.getElementById("popup-input");

		if(type=="image"){
			title.innerHTML = "Add image";
			userInput.placeholder="image url";
		}
		if(type=="link"){
			title.innerHTML = "Add link";
			userInput.placeholder="link url";
		}
		
		popupForm.onsubmit=function(){
			if(type=="link"){
				innerDoc.execCommand("createLink",false,userInput.value);
			}
			else if(type=="image"){
				innerDoc.execCommand("insertImage",false,userInput.value);
			}
			popup.style.display="none";
			userInput.value="";
			return false;
		}
		
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
	
	function checkFontFamily(fontFamily){
		var str = fontFamily.replace(/ /g,"-");
		console.log(str);
		var fontFamilyMenu = document.getElementById("font-family-menu");
		fontFamilyMenu.value = str;
	}
	
	function checkFontSize(size){
		var fontSizeMenu = document.getElementById("font-size-menu");
		fontSizeMenu.value = size;
	}
	
	
	function checkBold(style){
		var boldCell = document.getElementById("bold-cell");
		if(style=="bold"){
			isBold = true;
			boldCell.style.backgroundColor = "#A0A0A0";
		}
		else{
			isBold = false;
			boldCell.style.backgroundColor = "transparent";
		}
	}
	
	function checkItalic(style){
		var italicCell = document.getElementById("italic-cell");
		if(style=="italic"){
			isItalic = true;
			italicCell.style.backgroundColor = "#A0A0A0";
		}
		else{
			isItalic = false;
			italicCell.style.backgroundColor="transparent";
		}
	}
	
	
	function checkUnderline(style){
		var underlineCell = document.getElementById('underline-cell');
		if(style=="underline"){
			isUnderlined=true;
			underlineCell.style.backgroundColor = "#A0A0A0";
		}
		else{
			isUnderlinded = false;
			underlineCell.style.backgroundColor = "transparent";
		}
	}
	
	function checkFontColor(color){
		var fontColorIndicator = document.getElementById("font-color-indicator");
		if(color!=null&&color!=""){
			fontColorIndicator.style.backgroundColor = color;
		}
		else{
			fontColorIndicator.style.backgroundColor = "black";
		}
	}
	
	function copyTextStyle(src,des){
		des.style.fontFamily = src.style.fontFamily;
		des.style.color = src.style.color;
		des.style.fontSize = src.style.fontSize;
		des.style.fontWeight = src.style.fontWeight;
		des.style.fontStyle = src.style.fontStyle;
		des.style.textDecoration = src.style.textDecoration;
	}
	
	String.prototype.replaceAll = function(search, replacement) {
	    var target = this;
	    return target.replace(new RegExp(search, 'g'), replacement);
	};
	
	