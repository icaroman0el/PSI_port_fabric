package vazkii.psi.common.attribute.base;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import vazkii.psi.api.PsiAPI;
import vazkii.psi.common.lib.LibAttributeNames;

@EventBusSubscriber(modid = PsiAPI.MOD_ID)
public final class ModAttributes {
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, PsiAPI.MOD_ID);

	public static final Holder<Attribute> TOTAL_PSI = Registry.registerForHolder(
			BuiltInRegistries.ATTRIBUTE,
			PsiAPI.location(LibAttributeNames.TOTAL_PSI),
			new RangedAttribute(
					"attribute.psi.total_psi",
					5000,
					0,
					Integer.MAX_VALUE
			).setSyncable(true)
	);

	public static final Holder<Attribute> REGEN = Registry.registerForHolder(
			BuiltInRegistries.ATTRIBUTE,
			PsiAPI.location(LibAttributeNames.REGEN),
			new RangedAttribute(
					"attribute.psi.regen",
					25,
					0,
					Integer.MAX_VALUE
			).setSyncable(true)
	);

	@SubscribeEvent
	public static void addAttributesToPlayer(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, TOTAL_PSI);
		event.add(EntityType.PLAYER, REGEN);
	}
}
