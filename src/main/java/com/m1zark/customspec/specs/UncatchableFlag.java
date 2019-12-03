package com.m1zark.customspec.specs;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.SpecFlag;

import com.pixelmonmod.pixelmon.api.pokemon.SpecValue;
import net.minecraft.util.text.TextFormatting;

public class UncatchableFlag extends SpecFlag {
	public UncatchableFlag() {
		super("uncatchable");
	}
	
	@Override public void apply(Pokemon pokemon) {
		super.apply(pokemon);
		if (value == true)
			pokemon.setNickname(pokemon.getDisplayName() + TextFormatting.getValueByName("Red") + " (Uncatchable)");
		else
			pokemon.setNickname(pokemon.getSpecies().name);
	}
	
	@Override public SpecValue<Boolean> make(boolean value)
	{
		UncatchableFlag flag = new UncatchableFlag();
		flag.value = value;
		return flag;
	}
}
