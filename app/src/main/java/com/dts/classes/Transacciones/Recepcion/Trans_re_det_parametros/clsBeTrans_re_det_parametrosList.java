package com.dts.classes.Transacciones.Recepcion.Trans_re_det_parametros;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeTrans_re_det_parametrosList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_re_det_parametros> items = new List<clsBeTrans_re_det_parametros>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeTrans_re_det_parametros> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeTrans_re_det_parametros clsBeTrans_re_det_parametros) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeTrans_re_det_parametros> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeTrans_re_det_parametros> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeTrans_re_det_parametros get(int index) {
            return null;
        }

        @Override
        public clsBeTrans_re_det_parametros set(int index, clsBeTrans_re_det_parametros element) {
            return null;
        }

        @Override
        public void add(int index, clsBeTrans_re_det_parametros element) {

        }

        @Override
        public clsBeTrans_re_det_parametros remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeTrans_re_det_parametros> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeTrans_re_det_parametros> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeTrans_re_det_parametros> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}
