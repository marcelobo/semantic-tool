/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufjf.ontology.gnosis.similarity.combination;

import br.ufjf.ontology.gnosis.similarity.common.SimilarityVO;
import java.util.Collection;

/**
 *
 * @author Isabella Pires Capobiango
 */
public interface ICombination {
    Collection<SimilarityVO> explore(Collection<SimilarityVO> similaridade);
}
