function floatAlert(width,height,str,time) {
	// body...
	var dom = document;
	var body = dom.getElementsByTagName('body')[0];
	var div = dom.createElement('div');
	var string = dom.createTextNode(str);
	var p = dom.createElement("span");
	p.appendChild(string);
	body.appendChild(div);
	div.appendChild(p);
	//div.appendChild(string);
	p.style.textAlign='center';
	p.style.fontSize= '14px';
	div.style.position = 'fixed';
	div.style.top = '50%';
	div.style.left = '50%';
	div.style.background = 'rgba(0,0,0,.6)';
	div.style.width = width+'px';
	div.style.height = height+'px';
	div.style.color = '#fff';
	div.style.textAlign = 'center';
	div.style.boxShadow = '0 0 8px 3px rgba(0,0,0,.6)';
	div.style.padding = '10px';
	div.style.borderRadius = '5px';
	div.style.marginTop = -Math.floor(height/2)+'px';
	div.style.marginLeft = -Math.floor(width/2)+'px';
	div.style.filter = 'alpha(opacity=100)';
	div.style.opacity = '1';
	div.style.zIndex = '200';
	div.style.lineHeight = height+'px';
	setTimeout(function(){
		var a = 100;
		var timer = setInterval(function(){
			if(a==0){
				div.style.filter = 'alpha(opacity='+a+')';
				div.style.opacity = a/100;
				clearInterval(timer);
				body.removeChild(div);
			}else{
				div.style.filter = 'alpha(opacity='+a+')';
				div.style.opacity = a/100;
			}
			a-=10;
		},50);
	},time);
}