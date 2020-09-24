package ru.will0376.igui.buttons;

public enum Mouses {
	RMB, LMB, None;

	public boolean isRight(Mouses in) {
		return in == RMB;
	}
}
