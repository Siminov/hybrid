
function IInlineResource(inlineResource) {
	
	return {
	
		getInlineResources: inlineResource.getInlineResources,
		
		getInlineResource: inlineResource.getInlineResource,
		
		addInlineResource: inlineResource.addInlineResource,
		
		containInlineResource: inlineResource.containInlineResource		
	}
}