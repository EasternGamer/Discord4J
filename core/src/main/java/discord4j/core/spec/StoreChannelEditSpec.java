/*
 * This file is part of Discord4J.
 *
 * Discord4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Discord4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Discord4J.  If not, see <http://www.gnu.org/licenses/>.
 */
package discord4j.core.spec;

import com.darichey.discordjson.json.ChannelModifyRequest;
import com.darichey.discordjson.json.ImmutableChannelModifyRequest;
import com.darichey.discordjson.json.ImmutableOverwriteData;
import com.darichey.discordjson.json.OverwriteData;
import com.darichey.discordjson.possible.Possible;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.channel.StoreChannel;
import reactor.util.annotation.Nullable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Spec used to modify a guild {@link StoreChannel} settings.
 *
 * @see <a href="https://discordapp.com/developers/docs/resources/channel#modify-channel">Modify Channel</a>
 */
public class StoreChannelEditSpec implements AuditSpec<ChannelModifyRequest> {

    private final ImmutableChannelModifyRequest.Builder requestBuilder = ImmutableChannelModifyRequest.builder();
    @Nullable
    private String reason;

    /**
     * Sets the name for the {@link StoreChannel}.
     *
     * @param name The new name of the category.
     * @return This spec.
     */
    public StoreChannelEditSpec setName(String name) {
        requestBuilder.name(Possible.of(name));
        return this;
    }

    /**
     * Sets the position for the {@link StoreChannel}.
     *
     * @param position The raw position for the category.
     * @return This spec.
     */
    public StoreChannelEditSpec setPosition(int position) {
        requestBuilder.position(Possible.of(position));
        return this;
    }

    /**
     * Sets the permission overwrites for the {@link StoreChannel}.
     *
     * @param permissionOverwrites The {@code Set<PermissionOverwrite>} which contains overwrites for the category.
     * @return This spec.
     */
    public StoreChannelEditSpec setPermissionOverwrites(Set<? extends PermissionOverwrite> permissionOverwrites) {
        List<OverwriteData> raw = permissionOverwrites.stream()
            .map(o -> ImmutableOverwriteData.of(o.getTargetId().asString(), o.getType().getValue(),
                o.getAllowed().getRawValue(), o.getDenied().getRawValue()))
            .collect(Collectors.toList());

        requestBuilder.permissionOverwrites(Possible.of(raw));
        return this;
    }

    @Override
    public StoreChannelEditSpec setReason(@Nullable final String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    @Nullable
    public String getReason() {
        return reason;
    }

    @Override
    public ChannelModifyRequest asRequest() {
        return requestBuilder.build();
    }
}
