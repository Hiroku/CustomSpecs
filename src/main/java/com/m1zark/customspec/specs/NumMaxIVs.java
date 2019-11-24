package com.m1zark.customspec.specs;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.RandomHelper;
import com.pixelmonmod.pixelmon.api.pokemon.ISpecType;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.SpecValue;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;

public class NumMaxIVs extends SpecValue<Integer> implements ISpecType {
	public NumMaxIVs(Integer value) {
		super("nummaxivs", value);
	}
	
	@Override public List<String> getKeys() {
		return Arrays.asList("nummaxivs", "nummaxiv");
	}
	
	@Override public SpecValue<?> parse(String arg) {
		try {
			int count = Integer.parseInt(arg);
			if (count < 0 || count > 6) {
				return null;
			}
			return new NumMaxIVs(count);
		}
		catch (NumberFormatException nfe) {
			return null;
		}
	}
	
	@Override public SpecValue<?> readFromNBT(NBTTagCompound nbt) {
		return new NumMaxIVs(nbt.getInteger("nummaxivs"));
	}
	
	@Override public void writeToNBT(NBTTagCompound nbt, SpecValue<?> value) {
		nbt.setByte("nummaxivs", Byte.parseByte(value.value.toString()));
	}
	
	@Override public Class<? extends SpecValue<?>> getSpecClass() {
		return NumMaxIVs.class;
	}
	
	@Override public String toParameterForm(SpecValue<?> value) {
		return "nummaxivs:" + value.value.toString();
	}
	
	@Override public Class<Integer> getValueClass() {
		return Integer.class;
	}
	
	@Override public void apply(EntityPixelmon pixelmon) {
		this.apply(pixelmon.getPokemonData());
	}
	
	@Override public void apply(Pokemon pokemon) {
		int[] ivs = this.getIVsWithMax31(this.value);
		pokemon.getIVs().set(StatsType.HP, ivs[0]);
		pokemon.getIVs().set(StatsType.Attack, ivs[1]);
		pokemon.getIVs().set(StatsType.Defence, ivs[2]);
		pokemon.getIVs().set(StatsType.SpecialAttack, ivs[3]);
		pokemon.getIVs().set(StatsType.SpecialDefence, ivs[4]);
		pokemon.getIVs().set(StatsType.Speed, ivs[5]);
	}
	
	@Override public boolean matches(EntityPixelmon pixelmon) {
		return this.matches(pixelmon.getPokemonData());
	}
	
	@Override public boolean matches(Pokemon pokemon) {
		return this.count31s(new int[]{pokemon.getIVs().hp, pokemon.getIVs().attack, pokemon.getIVs().defence, pokemon.getIVs().specialAttack, pokemon.getIVs().specialDefence, pokemon.getIVs().speed}) >= this.value;
	}
	
	@Override public SpecValue<Integer> clone() {
		return new NumMaxIVs(this.value);
	}
	
	private int[] getIVsWithMax31(int num) {
		int[] ivs = new int[6];
		ArrayList<Object> slots = Lists.newArrayList((Object[])new Integer[]{0, 1, 2, 3, 4, 5});
		while (!slots.isEmpty()) {
			int slot = (Integer)slots.get(RandomHelper.getRandomNumberBetween(0, (slots.size() - 1)));
			ivs[slot] = num > 0 ? 31 : RandomHelper.getRandomNumberBetween(0, 31);
			slots.remove((Object)slot);
			--num;
		}
		return ivs;
	}
	
	private int count31s(int[] ivs) {
		int count = 0;
		for (int iv : ivs) {
			if (iv != 31) continue;
			++count;
		}
		return count;
	}
}
