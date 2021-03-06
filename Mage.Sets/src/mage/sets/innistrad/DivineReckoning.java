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
package mage.sets.innistrad;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 * @author nantuko
 */
public class DivineReckoning extends CardImpl {

    public DivineReckoning(UUID ownerId) {
        super(ownerId, 10, "Divine Reckoning", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");
        this.expansionSetCode = "ISD";

        // Each player chooses a creature he or she controls. Destroy the rest.
        this.getSpellAbility().addEffect(new DivineReckoningEffect());

        // Flashback {5}{W}{W}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{5}{W}{W}"), TimingRule.SORCERY));
    }

    public DivineReckoning(final DivineReckoning card) {
        super(card);
    }

    @Override
    public DivineReckoning copy() {
        return new DivineReckoning(this);
    }
}

class DivineReckoningEffect extends OneShotEffect {

    public DivineReckoningEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Each player chooses a creature he or she controls. Destroy the rest";
    }

    public DivineReckoningEffect(DivineReckoningEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Card> chosen = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);

                Target target = new TargetControlledPermanent(1, 1, new FilterControlledCreaturePermanent(), true);
                if (target.canChoose(player.getId(), game)) {
                    while (player.canRespond() && !target.isChosen() && target.canChoose(player.getId(), game)) {
                        player.chooseTarget(Outcome.Benefit, target, source, game);
                    }
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        chosen.add(permanent);
                    }
                }
            }

            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
                if (!chosen.contains(permanent)) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;

    }

    @Override
    public DivineReckoningEffect copy() {
        return new DivineReckoningEffect(this);
    }
}
