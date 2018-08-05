package discord4j.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * This represents a command dispatcher. Implementing this allows for custom command resolution logic (for example,
 * making certain providers available only on certain guilds, etc).
 *
 * @see CommandProvider
 */
@FunctionalInterface
public interface CommandDispatcher {

    /**
     * Called to handle the logic of invoking a command based on event context.
     *
     * @param event The event context.
     * @param providers The {@link discord4j.command.CommandProvider}s this dispatcher should consider for dispatching.
     * @param errorHandler The error handler to pass to command.
     * @return A mono which emits executed command.
     */
    Mono<? extends Command> dispatch(MessageCreateEvent event, Set<CommandProvider> providers,
                                     CommandErrorHandler errorHandler);
}