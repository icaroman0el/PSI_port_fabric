package net.neoforged.neoforge.common.conditions;

import com.mojang.serialization.MapCodec;

public interface ICondition {
	boolean test(IContext context);

	MapCodec<? extends ICondition> codec();

	interface IContext {}
}
