package main.pkg;

public class ShipFactory {
	
	public static Ship makeShip(ShipType type){
		
		switch(type){
		
			case LandShipTwoSegment:{
				return new LandShipTwoSegment();
			}
			case LandShipThreeSegment:{
				return new LandShipThreeSegment();
			}
			case LandShipFourSegment:{
				return new LandShipFourSegment();
			}
			case SeaShipOneSegment:{
				return new SeaShipOneSegment();
			}
			case SeaShipTwoSegment:{
				return new SeaShipTwoSegment();
			}
			case SeaShipThreeSegment:{
				return new SeaShipThreeSegment();
			}
			case SeaShipFourSegment:{
				return new SeaShipFourSegment();
			}
			case AirShip:{
				return new AirShip();
			}
			default: return null;
		
		}
		
	}
	
}
