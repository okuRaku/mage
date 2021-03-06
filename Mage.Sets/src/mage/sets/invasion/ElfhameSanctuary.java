/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.invasion;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Markedagain
 */
public class ElfhameSanctuary extends CardImpl {

    public ElfhameSanctuary(UUID ownerId) {
        super(ownerId, 185, "Elfhame Sanctuary", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "INV";

        // At the beginning of your upkeep, you may search your library for a basic land card, reveal that card, and put it into your hand. If you do, you skip your draw step this turn and shuffle your library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(new FilterBasicLandCard())), TargetController.YOU, true);
        ability.addEffect(new SkipDrawStepThisTurn());
        
        this.addAbility(ability);
    }

    public ElfhameSanctuary(final ElfhameSanctuary card) {
        super(card);
    }

    @Override
    public ElfhameSanctuary copy() {
        return new ElfhameSanctuary(this);
    }
}

class SkipDrawStepThisTurn extends ReplacementEffectImpl {

    public SkipDrawStepThisTurn() {
        super(Duration.UntilYourNextTurn, Outcome.Neutral);
        staticText = "Skip your draw step this turn";
    }

    public SkipDrawStepThisTurn(final SkipDrawStepThisTurn effect) {
        super(effect);
    }

    @Override
    public SkipDrawStepThisTurn copy() {
        return new SkipDrawStepThisTurn(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}