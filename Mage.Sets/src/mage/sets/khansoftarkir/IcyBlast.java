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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class IcyBlast extends CardImpl {

    public IcyBlast(UUID ownerId) {
        super(ownerId, 42, "Icy Blast", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{U}");
        this.expansionSetCode = "KTK";


        // Tap X target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect("X target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, new FilterCreaturePermanent(), false));
        
        // <i>Ferocious</i> - If you control a creature with power 4 or greater, those creatures don't untap during their controllers' next untap steps.
        Effect effect = new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersNextUntapStepTargetEffect(),
                new LockedInCondition(FerociousCondition.getInstance()));
        effect.setText("<br/><i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, those creatures don't untap during their controllers' next untap steps");
        this.getSpellAbility().addEffect(effect);
    }

    public IcyBlast(final IcyBlast card) {
        super(card);
    }
    
    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int numberToTap = ability.getManaCostsToPay().getX();
            numberToTap = Math.min(game.getBattlefield().count(new FilterCreaturePermanent(), ability.getSourceId(), ability.getControllerId(), game), numberToTap);
            ability.addTarget(new TargetCreaturePermanent(numberToTap));
        }
    }

    @Override
    public IcyBlast copy() {
        return new IcyBlast(this);
    }
}
