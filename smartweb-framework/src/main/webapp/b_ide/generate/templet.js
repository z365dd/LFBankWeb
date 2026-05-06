
 $(function () {
 	/*
	 * 提交按钮
	 */
 	
	$("#btnId_add").click(function(){
		
		var bootstrapValidator = $('#formId_744971').data('bootstrapValidator');
		if(bootstrapValidator===undefined){
			$("#formId_744971").bootstrapValidator({
				feedbackIcons: {
					valid: 'glyphicon glyphicon-ok',
					invalid: 'glyphicon glyphicon-remove',
					validating: 'glyphicon glyphicon-refresh'
				}
			});
			bootstrapValidator = $('#formId_744971').data('bootstrapValidator');
		}
		
		
		/**/
		bootstrapValidator.validate();
		var b = bootstrapValidator.isValid();
		alert(b);
	});
 });
 
 
 