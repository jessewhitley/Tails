/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014
 *
 * See LICENSE for full License
 */

package kihira.tails.api;

import kihira.tails.client.render.RenderTail;
import kihira.tails.common.TailInfo;
import net.minecraft.entity.EntityLivingBase;

public interface ITailRenderHelper {

    public void onPreRenderTail(EntityLivingBase entity, RenderTail tail, TailInfo info, double x, double y, double z);

}
