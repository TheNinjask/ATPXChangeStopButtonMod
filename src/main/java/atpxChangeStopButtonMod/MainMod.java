package atpxChangeStopButtonMod;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import pt.theninjask.AnotherTwitchPlaysX.gui.MainFrame;
import pt.theninjask.AnotherTwitchPlaysX.gui.mainMenu.MainMenuPanel;
import pt.theninjask.AnotherTwitchPlaysX.gui.mod.Mod;
import pt.theninjask.AnotherTwitchPlaysX.gui.mod.ModPanel;
import pt.theninjask.AnotherTwitchPlaysX.util.Constants;

@Mod
public class MainMod extends JPanel implements ModPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel press;

	public MainMod() {
		super(new GridLayout(2, 1));
		try {
			this.setBackground(Constants.TWITCH_COLOR);
			press = new JLabel(
					String.format("Press a key (Current: %s)", NativeKeyEvent.getKeyText(Constants.stopKey)));
			press.setForeground(Constants.TWITCH_COLOR_COMPLEMENT);
			press.setHorizontalAlignment(JLabel.CENTER);
			this.add(press);
			this.setFocusable(true);
			GlobalScreen.registerNativeHook();
			GlobalScreen.addNativeKeyListener(new NativeKeyListener() {

				@Override
				public void nativeKeyTyped(NativeKeyEvent e) {
					// DO NOTHING
				}

				@Override
				public void nativeKeyReleased(NativeKeyEvent e) {
					// DO NOTHING
				}

				@Override
				public void nativeKeyPressed(NativeKeyEvent e) {
					press.setText(
							String.format("Press a key (Current: %s)", NativeKeyEvent.getKeyText(e.getKeyCode())));
					Constants.stopKey = e.getKeyCode();
				}
			});
			JButton ok = new JButton("Confirm");
			ok.addActionListener(l -> {
				try {
					GlobalScreen.unregisterNativeHook();

					Constants.STRING_TO_KEYCODE.remove(NativeKeyEvent.getKeyText(Constants.stopKey));

					MainFrame.getInstance().replacePanel(MainMenuPanel.getInstance());
				} catch (NativeHookException e1) {
					Constants.showExceptionDialog(e1);
					MainMenuPanel.getInstance().getModButton().setEnabled(false);
					MainFrame.getInstance().replacePanel(MainMenuPanel.getInstance());
				}
			});
			this.add(ok);
			MainMenuPanel.getInstance().getModButton().setText("Change Stop Key");
		} catch (NativeHookException e) {
			Constants.showExceptionDialog(e);
			MainMenuPanel.getInstance().getModButton().setEnabled(false);
			MainFrame.getInstance().replacePanel(MainMenuPanel.getInstance());
		}
	}

	@Override
	public void refresh() {
		press.setText(String.format("Press a key (Current: %s)", NativeKeyEvent.getKeyText(Constants.stopKey)));
		try {
			GlobalScreen.registerNativeHook();
			GlobalScreen.addNativeKeyListener(new NativeKeyListener() {

				@Override
				public void nativeKeyTyped(NativeKeyEvent e) {
					// DO NOTHING
				}

				@Override
				public void nativeKeyReleased(NativeKeyEvent e) {
					// DO NOTHING
				}

				@Override
				public void nativeKeyPressed(NativeKeyEvent e) {
					press.setText(
							String.format("Press a key (Current: %s)", NativeKeyEvent.getKeyText(e.getKeyCode())));
					Constants.stopKey = e.getKeyCode();
				}
			});
		} catch (NativeHookException e) {
			Constants.showExceptionDialog(e);
			MainMenuPanel.getInstance().getModButton().setEnabled(false);
			MainFrame.getInstance().replacePanel(MainMenuPanel.getInstance());
		}
	}

	@Override
	public JPanel getJPanelInstance() {
		return this;
	}

}
