import acm.graphics.GObject;

public class Selection {

	private Pile origin;
	private Pile selection;
	private boolean active;
	private SpiderSolitairePanel panel;
	
	public Selection(SpiderSolitairePanel panel, Pile selection, Pile origin, double x, double y) {
		this.origin = origin;
		this.selection = selection;		
		this.panel = panel;
		
		active = (selection != null) && (origin != null);
		
		if (active) {
			panel.add(selection, x - 10, y -10);
		}
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void drag(double x, double y) {
		selection.setLocation(x, y);
	}
	public void drop(double x, double y) {
		if (isActive()) {
			panel.remove(selection);
			GObject target = panel.getElementAt(x, y);

			if (target instanceof Pile) {
				drop((Pile)target);
			} else {
				cancel();
			}
		}
	}
	
	private void drop(Pile target) {
		if (target.putDown(selection)) origin.flipTopCard();
		else cancel();
		active = false;
	}
	
	private void cancel() {
		origin.addAll(selection);
		active = false;
	}
}
