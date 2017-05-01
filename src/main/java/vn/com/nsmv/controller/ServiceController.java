package vn.com.nsmv.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class ServiceController
{

	/*@Autowired
	private BusinessCardService businessCardService;
	
	@RequestMapping(value = "/api/cards", method = RequestMethod.GET)
	public ResponseEntity<List<APIBusinessCard>> listCards(
		@RequestParam String maxId,
		@RequestParam long time, @RequestParam String historyMaxId,  @RequestParam long historyLastUpdate)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		List<APIBusinessCard> results = new ArrayList<APIBusinessCard>();
		List<BusinessCard> updatedCards = businessCardService.getListCards(name, maxId, time);
		List<BusinessCard> nonUpdatedCards = businessCardService.getListNonupdatedCards(name, maxId, time);
		for(BusinessCard item: nonUpdatedCards){
			List<History> histories = businessCardService.getHistories(item.getId(), historyMaxId, historyLastUpdate);
			if(histories.isEmpty()){
				continue;
			}
			results.add(new APIBusinessCard(null, histories, item.getId()));
		}

		for(BusinessCard item:updatedCards){
			results.add(new APIBusinessCard(item, businessCardService.getHistories(item.getId(), historyMaxId, historyLastUpdate)));
		}
		if (results.isEmpty())
		{
			return new ResponseEntity<List<APIBusinessCard>>(HttpStatus.NO_CONTENT);
			// or return HttpStatus.NOT_FOUND
		}
		ResponseEntity<List<APIBusinessCard>> respones = new ResponseEntity<List<APIBusinessCard>>(
				results,
			HttpStatus.OK);
		return respones;
	}
	@RequestMapping(value = "/api/card/viewImage/{imageId}", method = RequestMethod.GET)
	public @ResponseBody void getImage(
		HttpServletResponse response, HttpServletRequest request,
		@PathVariable(value = "imageId") String imageId) throws Exception
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userCd = auth.getName();
		BusinessCard businessCard = businessCardService
			.getListBusinessCardByCardId(userCd, imageId, 0);
		if (businessCard == null)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// Get image upload
		String imageUrl = businessCard.getImageUrl();
	
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		String[] parts=null;
		try
		{
			InputStream is = new FileInputStream(imageUrl);
			BufferedImage image = ImageIO.read(is);
			//ImageIO.write(image, "jpeg", jpegOutputStream);
			parts = imageUrl.split(Pattern.quote("."));
			ImageIO.write(image, parts[1], jpegOutputStream);
		}
		catch (IllegalArgumentException e)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	
		byte[] imgByte = jpegOutputStream.toByteArray();
	
		ServletContext context = request.getServletContext();
		String mimeType = context.getMimeType(imageUrl);
		  if (Utils.isEmpty(mimeType))
		  {
		   mimeType = "application/octet-stream";
		  }

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType(mimeType);
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(imgByte);
		responseOutputStream.flush();
		responseOutputStream.close();
	}*/
}
