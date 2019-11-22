package app.wegual.poc.restmsgsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.wegual.poc.common.model.ObjectCreate;

@RestController
public class MessageSenderRestController {
	
	@Autowired
	private ObjectCreateMessageSender sender;

	@PostMapping("/objectCreate")
	  String newEmployee(@RequestBody ObjectCreate newObject) {
		sender.sendMessage(newObject);
	    return "success";
	  }
}
