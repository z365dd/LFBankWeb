//封装的ArrayList对象
function ArrayList(){
	this.buffer=new Array();
	var args=ArrayList.arguments;
	if(args.length>0) this.buffer=args[0];
	this.length=this.buffer.length;
	function ListIterator(table,len){
		this.table=table;
		this.len=len;                          
		this.index=0;
		this.hasNext=hasNext;
		function hasNext() {
			return this.index< this.len;
		}
		this.next=next;
		function next() { 
			if(!this.hasNext())
				throw "No such Element!";
			return this.table[this.index++];
		}
	}
	/**取ArrayList的长度*/
	this.hashCode=hashCode;
	function hashCode(){
		var h=0;
		for(var i=0;i<this.lengh;i++)
			h+=this.buffer[i].hashCode();
		return h;
	}
	/**
	取ArrayList的长度
	*/
	this.size=size;
	function size(){  return this.length; }
	/**
	清空ArrayList
	*/
	this.clear=clear;
	function clear(){	this.length=0;	}
	/**
	把ArrayList置空
	*/
	this.isEmpty=isEmpty;
	function isEmpty(){	return this.length==0;	}
	/**
	把ArrayList转换成数组
	*/
	this.toArray=toArray;
	function toArray(){
		var copy=new Array();
		for(var i=0;i<this.length;i++){
			copy[i]=this.buffer[i];
		}
		return copy;
	}
	/**
	按下标取数据元素
	*/
	this.get=get;
	function get(index){
		if(index>=0 && index<this.length)
			return this.buffer[index];
	return null;
	}
	/**
	删除参数传入的key的值
	*/
	this.remove=remove;
	function remove(param){
		var index=0;
		if(isNaN(param)){
			index=this.indexOf(param);
		}
		else index=param;
		if(index>=0 && index<this.length){
			for(var i=index;i<this.length-1;i++)
				this.buffer[i]=this.buffer[i+1];
			this.length-=1;
			return true;
		}
		else return false;
	}
	/**
	添加
	*/
	this.add=add;
	function add(){
		var args=add.arguments;
		if(args.length==1){
			this.buffer[this.length++]=args[0];
			return true;
		}
		else if(args.length==2){
			var index=args[0];
			var obj=args[1];
			if(index>=0 && index<=this.length){
				for(var i=this.length;i>index;i--)
					this.buffer[i]=this.buffer[i-1];
				this.buffer[i]=obj;
				this.length+=1;
				return true;
			}
		}
		return false;
	}
	this.indexOf=indexOf;
	/**
	方法搜索在ArrayList是否出现了作为参数传递的对象的存在
	如果找到对象,则返回对象的起始位置，否则返回-1
	*/
	function indexOf(obj){
		for(var i=0;i<this.length;i++){
			if(this.buffer[i]==obj) return i;
			//if(this.buffer[i].equals(obj)) return i;
		}
		return -1;
	}
	this.lastIndexOf=lastIndexOf;
	/**
	方法搜索在ArrayList是否出现了作为参数传递的对象的存在
	如果找到对象,则返回对象的最后位置，否则返回-1
	*/
	function lastIndexOf(obj){
		for(var i=this.length-1;i>=0;i--){
			if(this.buffer[i]==obj) return i;
			//if(this.buffer[i].equals(obj)) return i;
		}
		return -1;
	}
	/**
	方法是判断对象是否存在ArrayList中
	如果存在返回true，否则返回false
	*/
	this.contains=contains;
	function contains(obj){
		return this.indexOf(obj)!=-1;
	}
	/**
	方法是判断对象是否与ArrayList相等
	*/
	this.equals=equals;
	function equals(obj){
		if(this.size()!=obj.size()) return false;
		for(var i=0;i<this.length;i++){
			if(!obj.contains(this.buffer[i])) return false;
		}
		return true;
	}
	/**
	把一个ArrayList对象追加到另一个ArrayList对象中
	*/
	this.addAll=addAll;
	function addAll(list){
		var mod=false;
		for(var it=list.iterator();it.hasNext();){
			var v=it.next();
			if(this.add(v)) mod=true;
		}
		return mod;  
	}
	/**
	判断两个ArrayList对象是否相同
	相同返回true，否则返回false
	*/
	this.containsAll=containsAll;
	function containsAll(list){
		for(var i=0;i<list.size();i++){
			if(!this.contains(list.get(i))) return false;
		}
		return true;
	}
	/**
	删除ArrayList中参数传入的ArrayList中的对象
	*/
	this.removeAll=removeAll;
	function removeAll(list){
		for(var i=0;i<list.size();i++){
			this.remove(this.indexOf(list.get(i)));
		}
	}
	/**
	取与参数传入的ArrayList的交集
	*/
	this.retainAll=retainAll;
	function retainAll(list){
		for(var i=this.length-1;i>=0;i--){
			if(!list.contains(this.buffer[i])){
				this.remove(i);
			}
		}
	}
	/**
	取子ArrayList,begin为开始的记录数，end为结束的记录数
	*/
	this.subList=subList;
	function subList(begin,end){
		if(begin<0) begin=0;
			if(end>this.length) end=this.length;
		var newsize=end-begin;
		var newbuffer=new Array();
		for(var i=0;i<newsize;i++){
			newbuffer[i]=this.buffer[begin+i];
		}
		return new ArrayList(newbuffer);
	}
	/**
	把参数obj对象按index指定的下标存入的ArrayList中
	*/
	this.set=set;
	function set(index,obj){
		if(index>=0 && index<this.length){
			temp=this.buffer[index];
			this.buffer[index]=obj;
		return temp;
		}
	}
	
	this.iterator=iterator;
	function iterator(){
		return new ListIterator(this.buffer,this.length);
	}
}

//封装的Hashtable对象
function Hashtable()
{
	this._hash = new Object();
	this.add = function(key,value){
		if(typeof(key)!="undefined"){
			//if(this.contains(key)==false){
				this._hash[key]=typeof(value)=="undefined"?null:value;
				return true;
			//} else {
			//	return false;
			//}
		} else {
		return false;
		}
	}
	this.remove = function(key){delete this._hash[key];}
	
	this.size = function(){var i=0;for(var k in this._hash){i++;} return i;}
	
	this.get = function(key){return this._hash[key];}
	
	this.containsKey = function(key){ return typeof(this._hash[key])!="undefined";}
	
	this.clear = function(){for(var k in this._hash){delete this._hash[k];}}
	
	this.values = function() 
	{ var list = new ArrayList(); 
	  for(var k in this._hash){	
		  list.add(this._hash[k]); 
	  } 
	  return list;
	 }
	 
	this.keys = function() 
	{ var list = new ArrayList(); 
	  for(var k in this._hash){	
		  list.add(k); 
	  } 
	  return list;
	 }
	
	this.toStr = function() { 
	  var str = '';
	  for(var k in this._hash){	
		  str = k +":"+this._hash[k]+";";
	  } 
	  return str;
	}
}

