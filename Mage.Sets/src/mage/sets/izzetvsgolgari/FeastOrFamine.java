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
package mage.sets.izzetvsgolgari;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class FeastOrFamine extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact, nonblack creature");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public FeastOrFamine(UUID ownerId) {
        super(ownerId, 72, "Feast or Famine", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{B}");
        this.expansionSetCode = "DDJ";

        // Choose one - Put a 2/2 black Zombie creature token onto the battlefield; 
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken()));
        
        // or destroy target nonartifact, nonblack creature and it can't be regenerated.
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect(true));
        mode.getTargets().add(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    public FeastOrFamine(final FeastOrFamine card) {
        super(card);
    }

    @Override
    public FeastOrFamine copy() {
        return new FeastOrFamine(this);
    }
}
