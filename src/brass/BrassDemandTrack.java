package brass;

import java.awt.Graphics;

import java.util.List;
import java.util.ArrayList;

import gui.PixelPoint;
import gui.PixelDimension;
import gui.DrawImage;

public class BrassDemandTrack
{
	private util.StackLinked<BrassDemandTrackItem> buy_stack;
	private util.StackLinked<BrassDemandTrackItem> sell_stack;
	
	public static int getBrassDemandTrackValue(int demand_index)
	{
		if (demand_index > 8 || demand_index < 0) return 0;
		return ((9 - demand_index) + 1)/2;
	}
	
	public BrassDemandTrack(java.awt.Image track_img, PixelDimension track_dim, List<PixelPoint> track_centers)
	{
		buy_stack = new util.StackLinked<BrassDemandTrackItem>();
		sell_stack = new util.StackLinked<BrassDemandTrackItem>();
		
		BrassDemandTrackItem demand_track_bottom_item = new BrassDemandTrackItem(0, getBrassDemandTrackValue(0), null, null);
		buy_stack.push(demand_track_bottom_item);  //no image
		
		int track_size = track_centers.size();  //8 locations
		for (int i = 1; i <= track_size; i++)
		{
			DrawImage track_marker = new DrawImage(track_img, "Demand Marker", track_dim.getWidth(), track_dim.getHeight());
			BrassDemandTrackItem demand_track_item = new BrassDemandTrackItem(0, getBrassDemandTrackValue(i), track_centers.get(track_size - i), track_marker);
			buy_stack.push(demand_track_item);
			demand_track_item.showDemandTrackImage();
		}
	}
	
	public void draw(Graphics g)
	{
		util.StackLinked<BrassDemandTrackItem> draw_stack = new util.StackLinked<BrassDemandTrackItem>();
		while(buy_stack.size() > 1)  //bottom item not drawn
		{
			BrassDemandTrackItem item = buy_stack.pop();
			item.draw(g);
			draw_stack.push(item);
		}
		
		while(!draw_stack.isEmpty())
		{
			buy_stack.push(draw_stack.pop());
		}
	}
	
	public boolean canSellToDemandTrack()
	{
		return (!sell_stack.isEmpty());
	}
	
	public int sellToDemandTrack()
	{
		assert !sell_stack.isEmpty() : "Cannot sell to demand track.";
		
		BrassDemandTrackItem item = sell_stack.pop();
		int money = item.getAmount();
		buy_stack.push(item);

		return money;
	}
	
	public int getCostToBuyFromDemandTrack()
	{
		return buy_stack.peek().getAmount();
	}
	
	public void buyFromDemandTrack()
	{	
		//the last item is never popped
		if (buy_stack.size() > 1)
		{
			sell_stack.push(buy_stack.pop());
		}
	}
	
	org.jdom2.Element writeXML(String demand_track_name)
   {
      org.jdom2.Element demand_track_element = new org.jdom2.Element(demand_track_name);
	  demand_track_element.addContent(sell_stack.size() + "");

      return demand_track_element;
   }
   
	void readXML(org.jdom2.Element demand_track_element)
	{	
		int sell_stack_size = Integer.parseInt(demand_track_element.getText());

		while(sell_stack.size() > 0)
		{
			buy_stack.push(sell_stack.pop());
		}
		
		int i = 0;
		while(i < sell_stack_size)
		{
			sell_stack.push(buy_stack.pop());
			i++;
		}

	}
}
