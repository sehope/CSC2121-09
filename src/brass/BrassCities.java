package brass;

import java.awt.Graphics;

import gui.HotSpot;
import gui.PixelPoint;
import gui.PixelDimension;
import gui.DrawOval;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

class BrassCities
{
	private List<BrassCity> brass_cities;
	
	private DrawOval flip_circle;
	private PixelPoint flip_circle_center;
	
	private gui.DrawRectangle resource_cube;
	private List<PixelPoint> resource_cube_centers;
	
	public void placeTokenInCity(int city_id, BrassToken brass_token)
	{
		BrassCity brass_city = brass_cities.get(city_id - 1);
		brass_city.placeTokenInCity(brass_token);
	}
	
	public int getNumTokensOnBoard(int player_id)
	{
		int count = 0;
		Iterator<BrassCity> city_iter = brass_cities.iterator();
		while(city_iter.hasNext())
		{
			BrassCity brass_city = city_iter.next();
			count += brass_city.getNumTokensInCity(player_id);
		}
		return count;
	}
	
	public int getNumTokensInCity(int city_id, int player_id)
	{
		BrassCity brass_city = brass_cities.get(city_id - 1);
		return brass_city.getNumTokensInCity(player_id);
	}
	
	public boolean canCityAcceptIndustry(int city_id, int industry_id)
	{
		BrassCity brass_city = brass_cities.get(city_id - 1);
		return brass_city.canCityAcceptIndustry(industry_id);
	}
	
	public String getCityName(int city_id)
	{
		BrassCity brass_city = brass_cities.get(city_id - 1);
		return brass_city.getCityName();
	}
	
	public boolean isCityFull(int city_id)
	{
		BrassCity brass_city = brass_cities.get(city_id - 1);
		return brass_city.isCityFull();
	}
	
	public void draw(Graphics g)
	{
		Iterator<BrassCity> city_iter = brass_cities.iterator();
		while(city_iter.hasNext())
		{
			BrassCity brass_city = city_iter.next();
			brass_city.draw(g, resource_cube, resource_cube_centers, flip_circle, flip_circle_center);
		}
	}

	//if any of the industry locations within the city are selected, then the city is selected (1 to 19) 
	public int getSelectedCity(int x, int y)
	{
		Iterator<BrassCity> city_iter = brass_cities.iterator();
		while(city_iter.hasNext())
		{
			BrassCity brass_city = city_iter.next();
			//checks for any of the industries in the city
			if (brass_city.isCitySelected(x, y))
			{
				int brass_city_id = brass_city.getCityID();
				assert (brass_city_id >= 1 && brass_city_id <= 19) : "Get selected city error.";
				return brass_city_id;
			}
		}
		
		return 0;
	}
	
	//returns the hot spot id of the selected industry (1 to 42)
	public int getSelectedIndustry(int x, int y)
	{
		Iterator<BrassCity> city_iter = brass_cities.iterator();
		
		while(city_iter.hasNext())
		{
			BrassCity brass_city = city_iter.next();
			int brass_industry_id = brass_city.getSelectedIndustry(x, y);
			if (brass_industry_id > 0)
			{
				return brass_industry_id;
			}
		}
		
		return 0;
	}
	
	public BrassCities(BrassXML brass_xml)
	{
		PixelDimension resource_cube_dimension = brass_xml.getPixelDimension("cubes");
		resource_cube_centers = brass_xml.getPixelCenters("cubes");
		resource_cube = new gui.DrawRectangle(resource_cube_dimension.getWidth(), resource_cube_dimension.getHeight());
		
		PixelDimension flip_circle_dimension = brass_xml.getPixelDimension("flip");
		flip_circle_center = brass_xml.getPixelCenter("flip");
		flip_circle = new DrawOval(flip_circle_dimension.getWidth(), flip_circle_dimension.getHeight());
		
		brass_cities = new ArrayList<BrassCity>();
		
		PixelDimension city_dimension = brass_xml.getPixelDimension("cities");
		List<PixelPoint> industry_centers = brass_xml.getPixelCenters("cities");
		List<HotSpot> industry_hot_spots = new ArrayList<HotSpot>();
		
		for (int i = 1; i <= 43; i++)
		{
			PixelPoint pixel_point = industry_centers.get(i-1);
			HotSpot industry_hot_spot = new HotSpot(i, pixel_point.getX(), pixel_point.getY(), city_dimension.getWidth(), city_dimension.getHeight());
			industry_hot_spots.add(industry_hot_spot);
		}

		BrassCity brass_city = new BrassCity(1, "Barrow");
		BrassIndustry brass_industry = new BrassIndustry(5, industry_hot_spots.get(0));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(3, industry_hot_spots.get(1));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(2, "Birkenhead");
		brass_industry = new BrassIndustry(5, industry_hot_spots.get(2));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(3, "Blackburn");
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(3));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(4));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(3, industry_hot_spots.get(5));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(4, "Bolton");
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(6));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(7));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(3, industry_hot_spots.get(8));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(5, "Burnley");
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(9));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(10));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(6, "Bury");
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(11));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(12));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(7, "Colne");
		brass_industry = new BrassIndustry(2, industry_hot_spots.get(13));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(2, industry_hot_spots.get(14));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(8, "Ellesmere");
		brass_industry = new BrassIndustry(4, industry_hot_spots.get(15));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(9, "Fleetwood");
		brass_industry = new BrassIndustry(4, industry_hot_spots.get(16));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(10, "Lancaster");
		brass_industry = new BrassIndustry(4, industry_hot_spots.get(17));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(7, industry_hot_spots.get(18));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(11, "Liverpool");
		brass_industry = new BrassIndustry(4, industry_hot_spots.get(19));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(4, industry_hot_spots.get(20));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(5, industry_hot_spots.get(21));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(4, industry_hot_spots.get(22));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(12, "Macclesfield");
		brass_industry = new BrassIndustry(2, industry_hot_spots.get(23));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(2, industry_hot_spots.get(24));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(13, "Manchester");
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(25));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(26));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(27));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(3, industry_hot_spots.get(28));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(14, "Oldham");
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(29));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(30));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(15, "Preston");
		brass_industry = new BrassIndustry(4, industry_hot_spots.get(31));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(7, industry_hot_spots.get(32));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(3, industry_hot_spots.get(33));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);

		brass_city = new BrassCity(16, "Rochdale");
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(34));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(35));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(3, industry_hot_spots.get(36));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(17, "Stockport");
		brass_industry = new BrassIndustry(2, industry_hot_spots.get(37));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(2, industry_hot_spots.get(38));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		brass_city = new BrassCity(18, "Warrington");
		brass_industry = new BrassIndustry(6, industry_hot_spots.get(39));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(4, industry_hot_spots.get(40));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);

		brass_city = new BrassCity(19, "Wigan");
		brass_industry = new BrassIndustry(1, industry_hot_spots.get(41));
		brass_city.addIndustry(brass_industry);
		brass_industry = new BrassIndustry(1, industry_hot_spots.get(42));
		brass_city.addIndustry(brass_industry);
		brass_cities.add(brass_city);
		
		assert (brass_cities.size() == 19) : "Number of cities error.";
	}

}
