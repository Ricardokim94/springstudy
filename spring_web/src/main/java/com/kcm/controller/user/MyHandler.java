package com.kcm.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyHandler extends TextWebSocketHandler { //텍스트를 보낼수 있는 소캣임 ->택스트형식으로 바꿔서 보내야됨
	
	private static final Logger log = LoggerFactory.getLogger(MyHandler.class);
	private List<WebSocketSession> users;
	private Map<String, WebSocketSession> userMap; //Map은 키/값임 (Map은 키값중복x) - id가 키 ,session이 값
	
	public MyHandler() {		
		users = new ArrayList<WebSocketSession>();
		userMap = new HashMap<String, WebSocketSession>(); //hashMap형태로 메모리 확보하는 거임
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("TextWebSocketHandler : 연결 생성, session : " + session);
		users.add(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.info("TextWebSocketHandler : 메시지 수신, message : " + message.getPayload());		

		
		String msg = (String) message.getPayload(); //클라이언트가 보낸것
		JSONObject obj = new JSONObject(msg);		//json형태로 바꾼것
				
//		Iterator it = obj.keys();
//		while(it.hasNext()) {
//			String key = (String)it.next();			
//			log.info("key:" + key + ", value:" + obj.getString(key));						
//		}
		
		String type = obj.getString("type");
		if(obj != null && type.equals("register")) {
			String user = obj.getString("userid");
			userMap.put(user, session); //user에 session값 저장
		}else if(type.equals("chat")) { //chat 이면 대화방에 메세지 보내는것으로 정함
			String target = obj.getString("target");	//받는사람의 타겟을 가져옴 /받는 사람의 id가 target임
			WebSocketSession ws = userMap.get(target); 
			if(ws !=null) {
				String sendMsg = "[" + target + "]" + obj.getString("message");
				ws.sendMessage(new TextMessage(sendMsg));
			}	//ws이 null이 아니면 소켓에 연결이 되어 있는거임
		}
		
		
		
		
		
		
		
		
		
		
		
		/*		
		String type = obj.getString("type");
		if(obj != null && type.equals("register")) {
			String user = obj.getString("userid");
			userMap.put(user, session);
		} else {
			//클라이언트가 아이디, 메시지를 같이 보낸 경우
			String target = obj.getString("target");
			
			//대화상대방에 소켓에 연결되어 있는지 확인 후
			WebSocketSession ws = (WebSocketSession) userMap.get(target);
			String sendMsg = "[" + target + "] " + obj.getString("message");
			if(ws != null) {
				ws.sendMessage(new TextMessage(sendMsg));
			}
		}
		*/
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("TextWebSocketHandler : 연결 종료");
		users.remove(session);		
	}
	
	
}
