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
package discord4j.core.object.entity.channel;

import discord4j.common.util.Snowflake;
import discord4j.core.object.ExtendedInvite;
import discord4j.core.retriever.EntityRetrievalStrategy;
import discord4j.core.spec.InviteCreateMono;
import discord4j.core.spec.InviteCreateSpec;
import discord4j.core.spec.legacy.LegacyInviteCreateSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Consumer;

/** A Discord channel which can be categorized into a {@link Category}. These channels can also have invites. */
public interface CategorizableChannel extends TopLevelGuildChannel {

    /**
     * Gets the ID of the category for this channel, if present.
     *
     * @return The ID of the category for this channel, if present.
     */
    Optional<Snowflake> getCategoryId();

    /**
     * Requests to retrieve the category for this channel, if present.
     *
     * @return A {@link Mono} where, upon successful completion, emits the {@link Category category} this channel, if
     * present. If an error is received, it is emitted through the {@code Mono}.
     */
    Mono<Category> getCategory();

    /**
     * Requests to retrieve the category for this channel, if present, using the given retrieval strategy.
     *
     * @param retrievalStrategy the strategy to use to get the category
     * @return A {@link Mono} where, upon successful completion, emits the {@link Category category} this channel, if
     * present. If an error is received, it is emitted through the {@code Mono}.
     */
    Mono<Category> getCategory(EntityRetrievalStrategy retrievalStrategy);

    /**
     * Requests to create an invite.
     *
     * @param spec A {@link Consumer} that provides a "blank" {@link LegacyInviteCreateSpec} to be operated on.
     * @return A {@link Mono} where, upon successful completion, emits the created {@link ExtendedInvite}. If an error
     * is received, it is emitted through the {@code Mono}.
     * @deprecated use {@link #createInvite(InviteCreateSpec)} or {@link #createInvite()} which offer an immutable
     * approach to build specs
     */
    @Deprecated
    Mono<ExtendedInvite> createInvite(final Consumer<? super LegacyInviteCreateSpec> spec);

    /**
     * Requests to create an invite. Properties specifying how to create the invite can be set via the {@code withXxx }
     * methods of the returned {@link InviteCreateMono}.
     *
     * @return A {@link InviteCreateMono} where, upon successful completion, emits the created {@link ExtendedInvite}.
     * If an error is received, it is emitted through the {@code InviteCreateMono}.
     */
    default InviteCreateMono createInvite() {
        return InviteCreateMono.of(this);
    }

    /**
     * Requests to create an invite.
     *
     * @param spec an immutable object that specifies how to create the invite
     * @return A {@link Mono} where, upon successful completion, emits the created {@link ExtendedInvite}. If an error
     * is received, it is emitted through the {@code Mono}.
     */
    Mono<ExtendedInvite> createInvite(InviteCreateSpec spec);

    /**
     * Requests to retrieve this channel's invites.
     *
     * @return A {@link Flux} that continually emits this channel's {@link ExtendedInvite invites}. If an error is
     * received, it is emitted through the {@code Flux}.
     */
    Flux<ExtendedInvite> getInvites();
}
