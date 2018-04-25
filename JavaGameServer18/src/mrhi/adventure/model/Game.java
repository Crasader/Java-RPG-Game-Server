package mrhi.adventure.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrhi.adventure.db.ResourceDAO;
import mrhi.adventure.model.game.GameMap;
import mrhi.adventure.model.game.JItem;
import mrhi.adventure.model.game.JMap;
import mrhi.adventure.model.game.JMob;

public class Game {
	public static int itemGenIdx = 0;
	public static int mobGenIdx = 0;
	private Map<Integer, JMap> protoMapMap = new HashMap<>();
	private Map<Integer, JMob> protoMobMap = new HashMap<>();
	private Map<Integer, JItem> protoItemMap = new HashMap<>();
	private Map<Integer, GameMap> existMap = new HashMap<>();
	private Map<Integer, Integer> expMap = new HashMap<>();

	public Game() {	
		ResourceDAO rDAO = new ResourceDAO();
		List<JMap> pMapList = rDAO.getMapInfoList();
		List<JMob> pMobList = rDAO.getMobInfoList();
		List<JItem> pItemList = rDAO.getItemInfoList();
		
		for(int i=1; i<100; i++)
			expMap.put(i, i*10);
		
		for(JMap pMap : pMapList) 
			protoMapMap.put(pMap.getMap_idx(), pMap);
		
		for(JMob pMob : pMobList) 
			protoMobMap.put(pMob.getMob_idx(), pMob);
		
		for(JItem pItem : pItemList) 
			protoItemMap.put(pItem.getItem_idx(), pItem);
	}
	
	public Map<Integer, Integer> getExpMap() {
		return expMap;
	}

	public void setExpMap(Map<Integer, Integer> expMap) {
		this.expMap = expMap;
	}

	public synchronized void createMap(int mapid) {
		GameMap gMap = new GameMap();
		gMap.setMap(protoMapMap.get(mapid).clone());
		existMap.put(mapid, gMap);
		// Mob ����� �����ڵ� ���߿��� �� ���� ����� ����ؾ��Ѵ�. �����ͺ��̽����� �ʿ� �ش��ϴ� ���� �����ͼ� 
		// �װ� �׷�����Ѵ�. �׸��� ���͸� �߰��ϴ� �޼���� �������ؾ���
	}
	
	public JItem createItem(int item_idx, int x, int y) {
		JItem item = Server.getInstance().getGame().getProtoItemMap().get(item_idx).clone();
		item.setX(x);
		item.setY(y);
		item.setGen_idx(itemGenIdx++);
		return item;
	}
	
	public JMob generateMob(int mob_index, int x, int y) {
		JMob jmob = Server.getInstance().getGame().getProtoMobMap().get(mob_index).clone();
		jmob.setGen_idx(mobGenIdx++);
		jmob.setX(x);
		jmob.setY(y);
		return jmob;
	}
	
	public Map<Integer, GameMap> getExistMap() {
		return existMap;
	}

	public GameMap requestMap(int mapid) {
		return existMap.get(mapid);
	}

	public Map<Integer, JMap> getProtoMapMap() {
		return protoMapMap;
	}

	public Map<Integer, JMob> getProtoMobMap() {
		return protoMobMap;
	}

	public Map<Integer, JItem> getProtoItemMap() {
		return protoItemMap;
	}
}
